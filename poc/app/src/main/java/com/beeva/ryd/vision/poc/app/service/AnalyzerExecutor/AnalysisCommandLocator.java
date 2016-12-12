package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;

import java.util.List;

public interface AnalysisCommandLocator {

    List<AnalysisCommand> getCommandsFor(VisionRequestor.RequestType requestType);
}
