package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.repository.ComparisonEntityRepository;
import com.beeva.ryd.vision.poc.app.service.requestor.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service("defaultAnalysisExecutor")
public class DefaultAnalysisExecutor implements AnalysisExecutor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AnalysisCommandLocator analysisCommandLocator;

    private final ComparisonEntityRepository comparisonEntityRepository;

    @Autowired
    public DefaultAnalysisExecutor(AnalysisCommandLocator analysisCommandLocator, ComparisonEntityRepository comparisonEntityRepository) {
        this.analysisCommandLocator = analysisCommandLocator;
        this.comparisonEntityRepository = comparisonEntityRepository;
    }

    @Override
    public CompletableFuture<List<Result>> analyze(AnalysisConfiguration configuration) {
        final List<AnalysisCommand> commands = analysisCommandLocator.getCommandsFor(configuration.getAnalysisType());
        final CompletableFuture<Result>[] futures = new CompletableFuture[commands.size()];
        for (int index = 0; index < futures.length; index++) {
            final AnalysisCommand command = commands.get(index);
            logger.info("Executing " + command.getClass().getSimpleName());

            CompletableFuture<Result> commandFuture = CompletableFuture.supplyAsync(() -> {
                Thread.currentThread().setName("analysis-" + command.getClass().getSimpleName());
                return command.executeAnalysis(configuration);
            });

            CompletableFuture<Result> exceptionally = commandFuture.exceptionally(throwable -> {
                logger.error("Error executing " + command.getClass().getSimpleName(), throwable);
                return new Result(Instant.now(), Instant.now(), null);
            });

            CompletableFuture<Result> resultCompletableFuture = exceptionally.whenCompleteAsync((result, throwable) ->
                    logger.info(command.getClass().getSimpleName() + " finished")
            );

            futures[index] = resultCompletableFuture;
        }

        return allOf(futures).whenComplete((results, throwable) -> comparisonEntityRepository.writeComparisonOnDb(configuration));
    }

    private static <T> CompletableFuture<List<T>> allOf(CompletableFuture<T>... cfs) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(cfs);
        return allDoneFuture.thenApply(it ->
                Arrays.stream(cfs).
                        map(future -> future.join()).
                        collect(Collectors.toList())
        );
    }
}
