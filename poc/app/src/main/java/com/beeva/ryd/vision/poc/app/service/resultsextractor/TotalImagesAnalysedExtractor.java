package com.beeva.ryd.vision.poc.app.service.resultsextractor;

import com.beeva.ryd.vision.poc.app.repository.ComparisonEntityRepository;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;

public class TotalImagesAnalysedExtractor {

    public static int totalImagesAnalysed(ComparisonEntityRepository comparisonEntityRepository, VisionRequestor.RequestType requestType) {
        return comparisonEntityRepository.findByType(requestType.toString()).stream()
                .mapToInt(value -> value.getImages().size())
                .sum();
    }
}
