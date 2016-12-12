package com.beeva.ryd.vision.poc.app.service.requestor;

import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;

import java.time.Instant;
import java.util.List;

abstract class AbstractVisionRequestor<T> implements VisionRequestor<T> {

    @Override
    public Result<T> requests(List<Image> imagePathList, VisionRequestor.RequestType requestType) {
        final Instant initInstant = Instant.now();
        final T invocationResult = executeRequest(imagePathList, requestType);
        final Instant endInstant = Instant.now();
        return new Result<>(initInstant, endInstant, invocationResult);
    }

    abstract T executeRequest(List<Image> imagePathList, VisionRequestor.RequestType requestType);

    abstract Class<T> getClassOfInvocation();
}
