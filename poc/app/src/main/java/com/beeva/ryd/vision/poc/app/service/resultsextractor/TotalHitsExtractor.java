package com.beeva.ryd.vision.poc.app.service.resultsextractor;

import com.beeva.ryd.vision.poc.app.controller.bean.Measure;
import com.beeva.ryd.vision.poc.app.entity.ComparisonEntity;
import com.beeva.ryd.vision.poc.app.entity.ExecutorSingleResult;
import com.beeva.ryd.vision.poc.app.entity.ImageComparison;
import com.beeva.ryd.vision.poc.app.repository.ComparisonEntityRepository;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TotalHitsExtractor {

    public static Measure createTotalHits(ComparisonEntityRepository comparisonEntityRepository,
                                          String measureName, List<String> executors, VisionRequestor.RequestType requestType, Function<ExecutorSingleResult, Boolean> function) {
        List<ComparisonEntity> comparisonEntities = comparisonEntityRepository.findByType(requestType.toString());

        final Map<String, Integer> values = new HashMap<>();
        for (ComparisonEntity comparisonEntity : comparisonEntities) {
            for (ImageComparison imageComparison : comparisonEntity.getImages()) {
                for (ExecutorSingleResult executorSingleResult : imageComparison.getExecutorSingleResultList()) {
                    if(function.apply(executorSingleResult)) {
                        Integer count = values.get(executorSingleResult.getExecutor());
                        if(count == null) {
                            count = Integer.valueOf(0);
                        }
                        count = count + 1;
                        values.put(executorSingleResult.getExecutor(), count);
                    }
                }
            }
        }

        final Measure measure = new Measure(measureName);
        for (String executor : executors) {
            final Integer result = values.get(executor) != null ?
                    values.get(executor)
                    : Integer.valueOf(0);

            measure.addValue(result.toString());
        }
        return measure;
    }
}
