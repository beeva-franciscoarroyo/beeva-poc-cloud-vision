package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.service.requestor.Result;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;

import java.util.List;

public interface AnalysisCommand {

    Result<?> executeAnalysis(AnalysisConfiguration configuration);

    List<VisionRequestor.RequestType> supportedDetections();
}
