package com.beeva.ryd.vision.poc.cognitiveservices;

import java.nio.file.Path;
import java.util.List;

public class CognitiveServicesService {

    private final String apiKey;

    public CognitiveServicesService(String apiKey) {
        this.apiKey = apiKey;
    }

    public CognitiveServiceAnalyzeResult analyze(List<Path> imagePathList, String visualFeatures)  {
        switch (visualFeatures) {
            case "Tags" :
                return new AnalyzeTagsRequest(apiKey).analyze(imagePathList);
            case "Ocr" :
                return new OcrRequest(apiKey).analyze(imagePathList);
            default:
                throw new IllegalArgumentException("VisualFeature " + visualFeatures + " not supported");
        }
    }
}
