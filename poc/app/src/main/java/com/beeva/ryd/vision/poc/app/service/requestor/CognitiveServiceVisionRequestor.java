package com.beeva.ryd.vision.poc.app.service.requestor;

import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import com.beeva.ryd.vision.poc.cognitiveservices.CognitiveServiceAnalyzeResult;
import com.beeva.ryd.vision.poc.cognitiveservices.CognitiveServicesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("cognitiveServiceVisionRequestor")
public class CognitiveServiceVisionRequestor extends AbstractVisionRequestor<CognitiveServiceAnalyzeResult> {

    private final CognitiveServicesService service;

    public CognitiveServiceVisionRequestor(@Value("${cognitiveservices.api.key}")String apiKey) {
        service = new CognitiveServicesService(apiKey);
    }

    @Override
    CognitiveServiceAnalyzeResult executeRequest(List<Image> imageList, RequestType requestType) {
        final List<Path> imagePathList = imageList.stream()
                .map(Image::getPath)
                .collect(Collectors.toList());
        final String visualFeature = getVisualFeature(requestType);
        return service.analyze(imagePathList, visualFeature);
    }

    //FIXME chage ocr_detection text
    private String getVisualFeature(RequestType requestType) {
        switch (requestType) {
            case OCR_DETECTION:
                return "Ocr";
            case LABEL_DETECTION:
                return "Tags";
        }
        return "Tags";
    }

    @Override
    Class<CognitiveServiceAnalyzeResult> getClassOfInvocation() {
        return CognitiveServiceAnalyzeResult.class;
    }

    @Override
    public List<RequestType> supportedRequestTypes() {
        return Arrays.asList(VisionRequestor.RequestType.OCR_DETECTION,
                VisionRequestor.RequestType.LABEL_DETECTION);
    }
}
