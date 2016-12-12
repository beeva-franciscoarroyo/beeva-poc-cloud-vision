package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.service.requestor.Result;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AnalysisExecutor {

    CompletableFuture<List<Result>> analyze(AnalysisConfiguration configuration);
}
