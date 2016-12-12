package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.entity.ExecutionEntity;
import com.beeva.ryd.vision.poc.app.repository.ExecutionEntityRepository;
import com.beeva.ryd.vision.poc.app.repository.Tess4JEntityRepository;
import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import com.beeva.ryd.vision.poc.app.service.requestor.Result;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;
import com.beeva.ryd.vision.poc.tess4j.Tess4JAnalyzeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("tess4jCommand")
public class Tess4JAnalysisCommand implements AnalysisCommand {

    private final Tess4JEntityRepository repository;

    private final VisionRequestor<Tess4JAnalyzeResult> visionRequestor;

    @Autowired
    private ExecutionEntityRepository executionEntityRepository;

    @Autowired
    public Tess4JAnalysisCommand(Tess4JEntityRepository repository
            , @Qualifier("tess4JVisionRequestor") VisionRequestor<Tess4JAnalyzeResult> visionRequestor) {
        this.repository = repository;
        this.visionRequestor = visionRequestor;
    }

    @Override
    public Result<Tess4JAnalyzeResult> executeAnalysis(AnalysisConfiguration configuration) {
        final Result<Tess4JAnalyzeResult> requests = visionRequestor.requests(configuration.getImageList(), configuration.getAnalysisType());
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
