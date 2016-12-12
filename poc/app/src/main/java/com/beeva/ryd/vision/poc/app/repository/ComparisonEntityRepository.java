package com.beeva.ryd.vision.poc.app.repository;

import com.beeva.ryd.vision.poc.app.entity.ComparisonEntity;
import com.beeva.ryd.vision.poc.app.entity.ImageComparison;
import com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor.AnalysisConfiguration;
import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ComparisonEntityRepository extends MongoRepository<ComparisonEntity, String> {

    List<ComparisonEntity> findByType(String type);

    default void writeComparisonOnDb(AnalysisConfiguration configuration) {

        final ComparisonEntity comparisonEntity = new ComparisonEntity();
        comparisonEntity.setName(configuration.getName());
        comparisonEntity.setType(configuration.getAnalysisType().toString());

        final List<ImageComparison> imageComparisonList = new ArrayList<>(configuration.getImageList().size());
        for (Image image : configuration.getImageList()) {
            final ImageComparison imageComparison = new ImageComparison();
            imageComparison.setPath(image.getPath().toString());
            imageComparison.setName(image.getPath().getFileName().toString());
            imageComparisonList.add(imageComparison);
        }
        comparisonEntity.setImages(imageComparisonList);
        save(comparisonEntity);
    }


}
