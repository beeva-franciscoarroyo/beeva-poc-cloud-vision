package com.beeva.ryd.vision.poc.cognitiveservices.bean;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeResponse {

    private List<Tag> tags = new ArrayList<>();

    private String requestId;

    private Metadata metadata;

    private Path path;
    private String name;

    private String error;

    public AnalyzeResponse() {
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
