package com.beeva.ryd.vision.poc.app.service.requestor;

import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import com.beeva.ryd.vision.poc.tess4j.Tess4J;
import com.beeva.ryd.vision.poc.tess4j.Tess4JAnalyzeResult;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("tess4JVisionRequestor")
public class Tess4JVisionRequestor extends AbstractVisionRequestor<Tess4JAnalyzeResult> {

    private final Tess4J tess4J = new Tess4J();

    @Override
    Tess4JAnalyzeResult executeRequest(List<Image> imagePathList, VisionRequestor.RequestType requestType) {
        final List<Path> images = imagePathList.stream()
                .map(image -> image.getPath())
                .collect(Collectors.toList());
        return tess4J.analyze(images);
    }

    @Override
    public Class<Tess4JAnalyzeResult> getClassOfInvocation() {
        return Tess4JAnalyzeResult.class;
    }

    @Override
    public List<RequestType> supportedRequestTypes() {
        return Arrays.asList(RequestType.OCR_DETECTION);
    }

}
