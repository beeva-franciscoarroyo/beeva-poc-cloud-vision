package com.beeva.ryd.vision.poc.app.entity;

import com.beeva.ryd.vision.poc.cloudvision.CloudVisionLabelDetectionResult;
import com.beeva.ryd.vision.poc.cloudvision.CloudVisionTextDetectionResult;
import org.springframework.data.annotation.Id;

import java.util.List;

public class CloudVisionEntity {

    @Id
    private String imagePath;

    private String name;

    private String error;

    private List<CloudVisionTextDetectionResult> textDetectionResultList;

    private List<CloudVisionLabelDetectionResult> labelDetectionResultList;

    public CloudVisionEntity() {
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
