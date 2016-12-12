package com.beeva.ryd.vision.poc.cognitiveservices;

import com.beeva.ryd.vision.poc.cognitiveservices.bean.AnalyzeResponse;
import com.beeva.ryd.vision.poc.cognitiveservices.bean.OcrResponse;

import java.util.ArrayList;
import java.util.List;

public class CognitiveServiceAnalyzeResult {

    private final List<AnalyzeResponse> responseList = new ArrayList<>();

    private final List<OcrResponse> ocrResponseList = new ArrayList<>();

    public CognitiveServiceAnalyzeResult(List<AnalyzeResponse> responseList, List<OcrResponse> ocrResponseList) {
        if(responseList != null) {
            this.responseList.addAll(responseList);
        }

        if(ocrResponseList != null) {
            this.ocrResponseList.addAll(ocrResponseList);
        }
    }

    public CognitiveServiceAnalyzeResult() {

    }

    public List<AnalyzeResponse> getResponseList() {
        return responseList;
    }

    public List<OcrResponse> getOcrResponseList() {
        return ocrResponseList;
    }
}
