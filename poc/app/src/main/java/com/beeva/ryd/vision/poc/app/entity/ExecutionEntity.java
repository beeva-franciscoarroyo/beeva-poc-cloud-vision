package com.beeva.ryd.vision.poc.app.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class ExecutionEntity {

    @Id
    private String id;

    private String type;

    private long executionTimeInMillis;

    private int numberOfRequest;

    private String executor;

    private LocalDateTime now = LocalDateTime.now();

    private List<String> imagePathList;

    public ExecutionEntity(String type, long executionTimeInMillis, int numberOfRequest, String executor, List<String> imagePathList) {
        this.type = type;
        this.executionTimeInMillis = executionTimeInMillis;
        this.numberOfRequest = numberOfRequest;
        this.executor = executor;
        this.imagePathList = imagePathList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getExecutionTimeInMillis() {
        return executionTimeInMillis;
    }

    public void setExecutionTimeInMillis(long executionTimeInMillis) {
        this.executionTimeInMillis = executionTimeInMillis;
    }

    public int getNumberOfRequest() {
        return numberOfRequest;
    }

    public void setNumberOfRequest(int numberOfRequest) {
        this.numberOfRequest = numberOfRequest;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public List<String> getImagePathList() {
        return imagePathList;
    }
}
