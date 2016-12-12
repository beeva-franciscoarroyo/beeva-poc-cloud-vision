package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.repository.ComparisonEntityRepository;
import com.beeva.ryd.vision.poc.app.service.requestor.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Primary
@Service("iteratorAnalysisExecutor")
public class IteratorAnalysisExecutor implements AnalysisExecutor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AnalysisCommandLocator analysisCommandLocator;

    private final ComparisonEntityRepository comparisonEntityRepository;

    @Autowired
    public IteratorAnalysisExecutor(AnalysisCommandLocator analysisCommandLocator, ComparisonEntityRepository comparisonEntityRepository) {
        this.analysisCommandLocator = analysisCommandLocator;
        this.comparisonEntityRepository = comparisonEntityRepository;
    }

    @Override
    public CompletableFuture<List<Result>> analyze(AnalysisConfiguration configuration) {
        final List<AnalysisCommand> commands = analysisCommandLocator.getCommandsFor(configuration.getAnalysisType());
        final List<Result> results = new ArrayList<>();
        for (AnalysisCommand command : commands) {
            logger.info("Executing " + command.getClass().getSimpleName());
            Result<?> result = command.executeAnalysis(configuration);
            logger.info("Time " + result.getExecutionTime());
            results.add(result);
            logger.info(command.getClass().getSimpleName() + " finished");
        }

        comparisonEntityRepository.writeComparisonOnDb(configuration);

        return CompletableFuture.completedFuture(results);
    }



}
