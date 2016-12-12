package com.beeva.ryd.vision.poc.app.service.resultsextractor;

import com.beeva.ryd.vision.poc.app.controller.bean.Measure;
import com.beeva.ryd.vision.poc.app.entity.ComparisonEntity;
import com.beeva.ryd.vision.poc.app.entity.ImageComparison;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MostMeasureExtractor {

    public static Measure createMostMeasure(String measureName, List<String> executors, List<ComparisonEntity> comparisonEntities, Function<ImageComparison, String> functionValue) {
        final Measure measure = new Measure(measureName);

        final Map<String, Integer> executorIndex = new HashMap<>(executors.size());
        final Map<Integer, Integer> indexValues = new HashMap<>(executors.size());

        for (int index = 0; index < executors.size(); index++) {
            executorIndex.put(executors.get(index), index);
            indexValues.put(index, 0);
        }

        for (ComparisonEntity comparisonEntity : comparisonEntities) {
            for (ImageComparison imageComparison : comparisonEntity.getImages()) {
                final String value = functionValue.apply(imageComparison);
                if(executorIndex.containsKey(value)) {
                    Integer index = executorIndex.get(value);
                    int newValue = indexValues.get(index).intValue() + 1;
                    indexValues.put(index, newValue);
                }
            }
        }

        for (int index = 0; index < executors.size(); index++) {
            measure.addValue(indexValues.get(index).toString());
        }

        return measure;
    }

}
