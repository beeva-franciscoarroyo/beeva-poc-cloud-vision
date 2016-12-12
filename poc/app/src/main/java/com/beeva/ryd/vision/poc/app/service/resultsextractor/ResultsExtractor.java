package com.beeva.ryd.vision.poc.app.service.resultsextractor;

import com.beeva.ryd.vision.poc.app.controller.bean.ResultsBean;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;

public interface ResultsExtractor {

    ResultsBean getResult(VisionRequestor.RequestType requestType);

}
