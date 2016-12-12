package com.beeva.ryd.vision.poc.app.service.requestor;

import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VisionRequestor<T> {

    enum RequestType {
        OCR_DETECTION,
        LABEL_DETECTION
    }

    Result<T> requests(List<Image> imagePaths, VisionRequestor.RequestType requestType);

    List<RequestType> supportedRequestTypes();

    

}
