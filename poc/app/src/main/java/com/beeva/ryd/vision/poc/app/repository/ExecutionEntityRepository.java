package com.beeva.ryd.vision.poc.app.repository;

import com.beeva.ryd.vision.poc.app.entity.ExecutionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionEntityRepository extends MongoRepository<ExecutionEntity, String> {

    List<ExecutionEntity> findByExecutor(String executor);

}
