package com.beeva.ryd.vision.poc.tess4j;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class Tess4JImageResult {

    private final Path imagePath;

    private List<String> results;
    private String error;

    public Tess4JImageResult(Path imagePath, List<String> results) {
        this.imagePath = imagePath;
        this.results = results;
    }

    public Tess4JImageResult(Path imagePath, String result) {
        this(imagePath, Collections.singletonList(result));
    }

    public Path getImagePath() {
        return imagePath;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Tess4JResult{" +
                "imagePath=" + imagePath +
                ", results=" + results +
                '}';
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
