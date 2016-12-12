package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;

import java.util.List;

public class AnalysisConfiguration {

    private final String name;

    private final List<Image> imageList;

    private final VisionRequestor.RequestType analysisType;

    public AnalysisConfiguration(String name, List<Image> imageList, VisionRequestor.RequestType analysisType) {
        this.name = name;
        this.imageList = imageList;
        this.analysisType = analysisType;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public VisionRequestor.RequestType getAnalysisType() {
        return analysisType;
    }

    public String getName() {
        return name;
    }
}
