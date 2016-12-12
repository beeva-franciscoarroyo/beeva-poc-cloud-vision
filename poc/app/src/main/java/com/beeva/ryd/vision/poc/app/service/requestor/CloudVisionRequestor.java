package com.beeva.ryd.vision.poc.app.service.requestor;

import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import com.beeva.ryd.vision.poc.cloudvision.CloudVision;
import com.beeva.ryd.vision.poc.cloudvision.CloudVisionAnalyzeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("cloudVisionRequestor")
public class CloudVisionRequestor extends AbstractVisionRequestor<CloudVisionAnalyzeResult> {

    private CloudVision cloudVision;

    public CloudVisionRequestor(@Value("${cloud.vision.keys.path}")String cloudVisionKeyPath) {
        final Path cloudVisionKeys = Paths.get(cloudVisionKeyPath);
        cloudVision = new CloudVision(cloudVisionKeys);
    }

    @Override
    CloudVisionAnalyzeResult executeRequest(List<Image> imageList, VisionRequestor.RequestType requestType) {
        final List<Path> imagePathList = imageList.stream()
                .map(image -> image.getPath())
                .collect(Collectors.toList());
        final List<String> features = getCloudVisionFeature(requestType);
        try {
            return cloudVision.analyze(imagePathList, features);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getCloudVisionFeature(RequestType requestType) {
        switch (requestType) {
            case OCR_DETECTION:
                return Arrays.asList("TEXT_DETECTION");
            case LABEL_DETECTION:
                return Arrays.asList("LABEL_DETECTION");
        }

        return Arrays.asList("TEXT_DETECTION");
    }

    @Override
    Class<CloudVisionAnalyzeResult> getClassOfInvocation() {
        return CloudVisionAnalyzeResult.class;
    }


    @Override
    public List<RequestType> supportedRequestTypes() {
        return Arrays.asList(VisionRequestor.RequestType.OCR_DETECTION,
                VisionRequestor.RequestType.LABEL_DETECTION);
    }
}
