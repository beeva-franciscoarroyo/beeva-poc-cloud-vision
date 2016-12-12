package com.beeva.ryd.vision.poc.app.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageComparison {

    private String name;

    private String path;

    private String mostPrecised;
    private String mostComplete;

    private List<ExecutorSingleResult> executorSingleResultList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMostPrecised() {
        return mostPrecised;
    }

    public void setMostPrecised(String mostPrecised) {
        this.mostPrecised = mostPrecised;
    }

    public void setMostComplete(String mostComplete) {
        this.mostComplete = mostComplete;
    }

    public String getMostComplete() {
        return mostComplete;
    }

    public boolean isCompared() {
        return !StringUtils.isEmpty(mostComplete) || !StringUtils.isEmpty(mostPrecised);
    }

    public List<ExecutorSingleResult> getExecutorSingleResultList() {
        return executorSingleResultList;
    }

    public void setExecutorSingleResultList(List<ExecutorSingleResult> executorSingleResultList) {
        this.executorSingleResultList = executorSingleResultList;
    }

    public void setExecutorSingleResultListFromJson(String executorsResult) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ExecutorSingleResult>> typeReference = new TypeReference<List<ExecutorSingleResult>>() {};
        List<ExecutorSingleResult> results = mapper.readValue(executorsResult, typeReference);

        results.forEach(executorSingleResult -> {
            executorSingleResult.updateName();
        });

        this.executorSingleResultList = results;
    }

    public String getExecutorSingleResultListJSON() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this.executorSingleResultList);
    }

}
