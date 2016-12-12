package com.beeva.ryd.vision.poc.app.repository;

import com.beeva.ryd.vision.poc.app.entity.Tess4JEntity;
import com.beeva.ryd.vision.poc.tess4j.Tess4JAnalyzeResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface Tess4JEntityRepository extends MongoRepository<Tess4JEntity, String>{

    default void save(Tess4JAnalyzeResult tess4JAnalyzeResult) {
        final List<Tess4JEntity> entities = tess4JAnalyzeResult.getImageResultList().stream()
                .map(Tess4JEntity::new)
                .collect(Collectors.toList());

        save(entities);
    }


    Tess4JEntity findByName(String imageName);
}
