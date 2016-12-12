package com.beeva.ryd.vision.poc.app.repository;

import com.beeva.ryd.vision.poc.app.entity.CognitiveServiceEntity;
import com.beeva.ryd.vision.poc.cognitiveservices.CognitiveServiceAnalyzeResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface CognitiveServiceEntityRepository extends MongoRepository<CognitiveServiceEntity, String> {

    default void save(CognitiveServiceAnalyzeResult result) {

        if(!result.getResponseList().isEmpty()) {
            final List<CognitiveServiceEntity> entities = result.getResponseList().stream()
                    .map(CognitiveServiceEntity::new)
                    .collect(Collectors.toList());
            save(entities);
        } else {
            final List<CognitiveServiceEntity> entities = result.getOcrResponseList().stream()
                    .map(CognitiveServiceEntity::new)
                    .collect(Collectors.toList());
            save(entities);
        }
    }

    CognitiveServiceEntity findByName(String imageName);
}
