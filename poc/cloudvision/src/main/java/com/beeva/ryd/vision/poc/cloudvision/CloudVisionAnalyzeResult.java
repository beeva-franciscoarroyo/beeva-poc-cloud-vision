package com.beeva.ryd.vision.poc.cloudvision;

import com.google.api.services.vision.v1.model.AnnotateImageResponse;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CloudVisionAnalyzeResult {

    private final List<CloudVisionImageResult> imageResultList = new ArrayList<>();

    public CloudVisionAnalyzeResult(List<Path> imagePathList, List<AnnotateImageResponse> results) {
        for (int index = 0; index < imagePathList.size(); index++) {
            imageResultList.add(new CloudVisionImageResult(imagePathList.get(index), results.get(index)));
        }
    }

    public List<CloudVisionImageResult> getImageResultList() {
        return imageResultList;
    }
}
