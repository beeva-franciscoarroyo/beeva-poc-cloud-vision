package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.entity.ExecutionEntity;
import com.beeva.ryd.vision.poc.app.repository.CloudVisionEntityRepository;
import com.beeva.ryd.vision.poc.app.repository.ExecutionEntityRepository;
import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import com.beeva.ryd.vision.poc.app.service.requestor.Result;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;
import com.beeva.ryd.vision.poc.cloudvision.CloudVisionAnalyzeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("cloudVisionAnalysisCommand")
public class CloudVisionAnalysisCommand implements AnalysisCommand {

    private final VisionRequestor<CloudVisionAnalyzeResult> visionRequestor;

    private final CloudVisionEntityRepository repository;

    @Autowired
    private ExecutionEntityRepository executionEntityRepository;

    @Autowired
    public CloudVisionAnalysisCommand(@Qualifier("cloudVisionRequestor") VisionRequestor<CloudVisionAnalyzeResult> visionRequestor
            , CloudVisionEntityRepository repository) {
        this.visionRequestor = visionRequestor;
        this.repository = repository;
    }

    @Override
    public Result<CloudVisionAnalyzeResult> executeAnalysis(AnalysisConfiguration configuration) {
        final Result<CloudVisionAnalyzeResult> requests = visionRequestor.requests(configuration.getImageList(), configuration.getAnalysisType());
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
