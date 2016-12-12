package com.beeva.ryd.vision.poc.app.service.resultsextractor;

import com.beeva.ryd.vision.poc.app.controller.bean.Measure;
import com.beeva.ryd.vision.poc.app.entity.ExecutionEntity;
import com.beeva.ryd.vision.poc.app.repository.ExecutionEntityRepository;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.util.List;

public class TotalTimeExtractor {

    public static Measure createTotalTimeMeasure(ExecutionEntityRepository executionEntityRepository,
                                           String measureName, List<String> executors, VisionRequestor.RequestType requestType) {
        final Measure measure = new Measure(measureName);
        for (String executor : executors) {
            long total = executionEntityRepository.findByExecutor(executor).stream()
                    .filter(executionEntity -> requestType.toString().equalsIgnoreCase(executionEntity.getType()))
                    .mapToLong(ExecutionEntity::getExecutionTimeInMillis)
                    .sum();

            final String duration = DurationFormatUtils.formatDuration(total, "HH:mm:ss:SSS");
            measure.addValue(duration);
        }
        return measure;
    }

}
