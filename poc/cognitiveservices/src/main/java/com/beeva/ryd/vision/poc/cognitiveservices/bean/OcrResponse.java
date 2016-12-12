package com.beeva.ryd.vision.poc.cognitiveservices.bean;

import java.nio.file.Path;
import java.util.List;

public class OcrResponse {

    private String language;

    private float textAngle;

    private String orientation;

    private List<Region> regions;
    private String name;
    private Path path;
    private String error;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public float getTextAngle() {
        return textAngle;
    }

    public void setTextAngle(float textAngle) {
        this.textAngle = textAngle;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}