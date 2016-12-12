package com.beeva.ryd.vision.poc.cloudvision;

import com.google.api.services.vision.v1.model.AnnotateImageResponse;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CloudVisionImageResult {

    private final Path imagePath;

    private String errorMessage;

    private List<CloudVisionTextDetectionResult> textDetectionResultList = new ArrayList<>();

    private List<CloudVisionLabelDetectionResult> labelDetectionResultList = new ArrayList<>();

    public CloudVisionImageResult(Path imagePath, AnnotateImageResponse annotateImageResponse) {
        this.imagePath = imagePath;

        if(annotateImageResponse != null) {
            if (annotateImageResponse.getTextAnnotations() != null && !annotateImageResponse.getTextAnnotations().isEmpty()) {
                this.textDetectionResultList.addAll(annotateImageResponse.getTextAnnotations().stream()
                        .map(CloudVisionTextDetectionResult::new)
                        .collect(Collectors.toList()));
            }

            if (annotateImageResponse.getLabelAnnotations() != null && !annotateImageResponse.getLabelAnnotations().isEmpty()) {
                this.labelDetectionResultList.addAll(annotateImageResponse.getLabelAnnotations().stream()
                        .map(CloudVisionLabelDetectionResult::new)
                        .collect(Collectors.toList()));
            }
        }

        if(annotateImageResponse.getError() != null) {
            this.errorMessage = annotateImageResponse.getError().getMessage();
        }

    }

    public Path getImagePath() {
        return imagePath;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<CloudVisionTextDetectionResult> getTextDetectionResultList() {
        return textDetectionResultList;
    }

    public void setTextDetectionResultList(List<CloudVisionTextDetectionResult> textDetectionResultList) {
        this.textDetectionResultList = textDetectionResultList;
    }

    public List<CloudVisionLabelDetectionResult> getLabelDetectionResultList() {
        return labelDetectionResultList;
    }

    public void setLabelDetectionResultList(List<CloudVisionLabelDetectionResult> labelDetectionResultList) {
        this.labelDetectionResultList = labelDetectionResultList;
    }
}
