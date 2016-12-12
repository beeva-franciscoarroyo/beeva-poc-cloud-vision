package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.entity.ExecutionEntity;
import com.beeva.ryd.vision.poc.app.repository.CognitiveServiceEntityRepository;
import com.beeva.ryd.vision.poc.app.repository.ExecutionEntityRepository;
import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import com.beeva.ryd.vision.poc.app.service.requestor.Result;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;
import com.beeva.ryd.vision.poc.cognitiveservices.CognitiveServiceAnalyzeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("cognitiveServicesAnalysisCommand")
public class CognitiveServicesAnalysisCommand implements AnalysisCommand {

    private final VisionRequestor<CognitiveServiceAnalyzeResult> visionRequestor;

    private final CognitiveServiceEntityRepository repository;

    @Autowired
    private ExecutionEntityRepository executionEntityRepository;

    @Autowired
    public CognitiveServicesAnalysisCommand(VisionRequestor<CognitiveServiceAnalyzeResult> visionRequestor, CognitiveServiceEntityRepository repository) {
        this.visionRequestor = visionRequestor;
        this.repository = repository;
    }

    @Override
    public Result<CognitiveServiceAnalyzeResult> executeAnalysis(AnalysisConfiguration configuration) {
        final Result<CognitiveServiceAnalyzeResult> requests = visionRequestor.requests(configuration.getImageList(), configuration.getAnalysisType());
        repository.save(requests.getInvocationResult());
        executionEntityRepository.save(new ExecutionEntity(configuration.getAnalysisType().toString()
                , requests.getExecutionTime()
                , configuration.getImageList().size()
                , visionRequestor.getClass().getSimpleName()
                , getImagePathList(configuration.getImageList())));
        return requests;
    }

    private List<String> getImagePathList(List<Image> imageList) {
        return imageList.stream().map(image -> image.getPath().toString()).collect(Collectors.toList());
    }

    @Override
    public List<VisionRequestor.RequestType> supportedDetections() {
        return visionRequestor.supportedRequestTypes();
    }
}
