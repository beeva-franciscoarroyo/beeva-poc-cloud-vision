package com.beeva.ryd.vision.poc.cloudvision;

import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.model.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CloudVision {

    private final Vision vision;

    public CloudVision(Path cloudVisionKeys) {
        final CliCredentialsFactory credentialsFactory = new CliCredentialsFactory(cloudVisionKeys);
        vision = CloudVisionServiceFactory.create(credentialsFactory);
    }

    public CloudVisionAnalyzeResult analyze(List<Path> imagePathList, List<String> features) throws IOException {
        final List<AnnotateImageResponse> results = new ArrayList<>(imagePathList.size());
        //I do a request for every image instead on sending all on bath because comparison purposes
        for (Path path : imagePathList) {
            final BatchAnnotateImagesResponse batchResponse = getBatchAnnotateImagesResponse(Collections.singletonList(path), features);
             if (batchResponse.getResponses() != null) {
                results.addAll(batchResponse.getResponses());
            } else {
                AnnotateImageResponse annotateImageResponse = new AnnotateImageResponse();
                annotateImageResponse.setLabelAnnotations(Collections.emptyList());
                annotateImageResponse.setTextAnnotations(Collections.emptyList());
                results.add(annotateImageResponse);
            }
        }

        return new CloudVisionAnalyzeResult(imagePathList, results);
    }

    private BatchAnnotateImagesResponse getBatchAnnotateImagesResponse(List<Path> imagePathList, List<String> features) throws IOException {
        final BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(createAnnotateImageRequest(imagePathList, parseFeatures(features)));
        final Vision.Images.Annotate annotate = vision.images().annotate(batchAnnotateImagesRequest);
        try {
            return annotate.execute();
        } catch (Exception exception) {
            final BatchAnnotateImagesResponse response = new BatchAnnotateImagesResponse();
            AnnotateImageResponse annotateImageResponse = new AnnotateImageResponse();
            Status status = new Status();
            status.setMessage(exception.getMessage());
            annotateImageResponse.setError(status);
            response.setResponses(Collections.singletonList(annotateImageResponse));
            return response;
        }
    }

    private List<Feature> parseFeatures(List<String> features) {
        final List<Feature> featuresParsedList = new ArrayList<>(features.size());
        for (String feature : features) {
            final Feature featureParsed = new Feature();
            featureParsed.setType(feature);
            featuresParsedList.add(featureParsed);
        }
        return featuresParsedList;
    }

    private List<AnnotateImageRequest> createAnnotateImageRequest(List<Path> imagePathList, List<Feature> features) throws IOException {
        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>(imagePathList.size());
        for (Path path : imagePathList) {
            ImageRequestBuilder imageRequestBuilder = new ImageRequestBuilder();
            imageRequestBuilder.addFeatures(features);
            imageRequestBuilder.addImageOnDisk(path);
            AnnotateImageRequest annotateImageRequest = imageRequestBuilder.build();
            annotateImageRequests.add(annotateImageRequest);
        }
        return annotateImageRequests;
    }
}
