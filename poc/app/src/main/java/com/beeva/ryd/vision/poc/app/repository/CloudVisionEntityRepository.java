package com.beeva.ryd.vision.poc.app.repository;

import com.beeva.ryd.vision.poc.app.entity.CloudVisionEntity;
import com.beeva.ryd.vision.poc.cloudvision.CloudVisionAnalyzeResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface CloudVisionEntityRepository  extends MongoRepository<CloudVisionEntity, String> {

    default void save(CloudVisionAnalyzeResult cloudVisionAnalyzeResult) {
        final List<CloudVisionEntity> cloudVisionEntities = cloudVisionAnalyzeResult.getImageResultList().stream()
                .map(cloudVisionImageResult -> {
                    final CloudVisionEntity cloudVisionEntity = new CloudVisionEntity();
                    cloudVisionEntity.setName(cloudVisionImageResult.getImagePath().getFileName().toString());
                    cloudVisionEntity.setError(cloudVisionImageResult.getErrorMessage());
                    cloudVisionEntity.setImagePath(cloudVisionImageResult.getImagePath().toString());
                    cloudVisionEntity.setLabelDetectionResultList(cloudVisionImageResult.getLabelDetectionResultList());
                    cloudVisionEntity.setTextDetectionResultList(cloudVisionImageResult.getTextDetectionResultList());
                    return cloudVisionEntity;
                }).collect(Collectors.toList());

        save(cloudVisionEntities);
    }

    CloudVisionEntity findByName(String imageName);
}
