package com.beeva.ryd.vision.poc.app.service.resultsextractor;

import com.beeva.ryd.vision.poc.app.controller.bean.ResultsBean;
import com.beeva.ryd.vision.poc.app.entity.ComparisonEntity;
import com.beeva.ryd.vision.poc.app.entity.ExecutorSingleResult;
import com.beeva.ryd.vision.poc.app.entity.ImageComparison;
import com.beeva.ryd.vision.poc.app.repository.ComparisonEntityRepository;
import com.beeva.ryd.vision.poc.app.repository.ExecutionEntityRepository;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DefaultResultsExtractor implements ResultsExtractor {

    @Autowired
    private ExecutionEntityRepository executionEntityRepository;

    @Autowired
    private ComparisonEntityRepository comparisonEntityRepository;

    @Override
    public ResultsBean getResult(VisionRequestor.RequestType requestType) {
        final ResultsBean resultsBean = new ResultsBean();
        final List<String> executors = getExecutors(requestType);
        resultsBean.setHeaders(executors);

        resultsBean.setTotalImageCount(TotalImagesAnalysedExtractor.totalImagesAnalysed(comparisonEntityRepository, requestType));

        final List<ComparisonEntity> comparisonEntities = comparisonEntityRepository.findByType(requestType.toString());
        resultsBean.addMeasure(MostMeasureExtractor.createMostMeasure("Most Complete", executors, comparisonEntities, ImageComparison::getMostComplete));
        resultsBean.addMeasure(MostMeasureExtractor.createMostMeasure("Most Precise", executors, comparisonEntities, ImageComparison::getMostPrecised));
        resultsBean.addMeasure(TotalTimeExtractor.createTotalTimeMeasure(executionEntityRepository, "Total time", executors, requestType));
        resultsBean.addMeasure(TotalHitsExtractor.createTotalHits(comparisonEntityRepository, "Total hits", executors, requestType, ExecutorSingleResult::isValue));
        resultsBean.addMeasure(TotalHitsExtractor.createTotalHits(comparisonEntityRepository, "Total failures", executors, requestType, executorSingleResult -> !executorSingleResult.isValue()));

        return resultsBean;
    }

    public List<String> getExecutors(VisionRequestor.RequestType requestType) {
        final Set<String> executors = new HashSet<>();
        executionEntityRepository.findAll()
                .stream()
                .filter(executionEntity -> requestType.toString().equalsIgnoreCase(executionEntity.getType()))
                .forEach(executionEntity -> executors.add(executionEntity.getExecutor()));
        return new ArrayList<>(executors);
    }




}
