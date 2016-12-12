package com.beeva.ryd.vision.poc.app.entity;

import com.beeva.ryd.vision.poc.tess4j.Tess4JImageResult;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Tess4JEntity {

    @Id
    private String imagePath;

    private String name;

    private List<String> results;

    private String detectionType;

    private String error;

    public Tess4JEntity() {
    }

    public Tess4JEntity(String name, String imagePath, List<String> results, String detectionType, String error) {
        this.imagePath = imagePath;
        this.results = results;
        this.detectionType = detectionType;
        this.name = name;
        this.error = error;
    }

    public Tess4JEntity(Tess4JImageResult tess4JImageResult) {
        this.imagePath = tess4JImageResult.getImagePath().toString();
        this.results = tess4JImageResult.getResults();
        this.detectionType = "OCR_DETECTION";
        this.name = tess4JImageResult.getImagePath().getFileName().toString();
        this.error = tess4JImageResult.getError();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public String getDetectionType() {
        return detectionType;
    }

    public void setDetectionType(String detectionType) {
        this.detectionType = detectionType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
