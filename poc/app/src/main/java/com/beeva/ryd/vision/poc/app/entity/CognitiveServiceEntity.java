package com.beeva.ryd.vision.poc.app.entity;

import com.beeva.ryd.vision.poc.cognitiveservices.bean.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CognitiveServiceEntity {


    @Id
    private String imagePath;

    private String name;

    private List<Tag> tags = new ArrayList<>();

    private String requestId;

    private Metadata metadata;

    private String language;
    private String orientation;
    private List<Region> regions = new ArrayList<>();
    private float textAngle;

    private String error;

    public CognitiveServiceEntity() {
    }

    public CognitiveServiceEntity(AnalyzeResponse response) {
        this.name = response.getName();
        this.imagePath = response.getPath().toString();
        this.tags = response.getTags();
        this.requestId = response.getRequestId();
        this.metadata = response.getMetadata();
        this.error = response.getError();
    }

    public CognitiveServiceEntity(OcrResponse response) {
        this.name = response.getName();
        this.imagePath = response.getPath().toString();
        this.language = response.getLanguage();
        this.orientation = response.getOrientation();
        this.regions = response.getRegions();
        this.textAngle = response.getTextAngle();
        this.error = response.getError();
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public float getTextAngle() {
        return textAngle;
    }

    public void setTextAngle(float textAngle) {
        this.textAngle = textAngle;
    }

    public List<String> getLines() {
//        return getAsASingleLine();
//        return getIndepententLines();

        final List<String> lines = new ArrayList<>();

        final List<String> asASingleLine = getAsASingleLine();
        if(asASingleLine != null) {
            lines.addAll(asASingleLine);
        }

        final Collection<? extends String> words = getWords();
        if (words != null) {
            lines.addAll(words);
        }

        return lines;
    }

    private Collection<? extends String> getWords() {
        final List<String> words = new ArrayList<>();

        for (Region region : getRegions()) {
            for (Line line : region.getLines()) {
                for (Word word : line.getWords()) {
                    words.add(word.getText());
                }
            }
        }
        return words;
    }

    private List<String> getAsASingleLine() {
        final List<String> lines = new ArrayList<>();
        final StringBuilder lineBuilder = new StringBuilder();
        for (Region region : getRegions()) {
            for (Line line : region.getLines()) {
                for (Word word : line.getWords()) {
                    lineBuilder.append(word.getText()).append(" ");
                }
            }
        }
        lines.add(lineBuilder.toString());
        return lines;
    }

    private List<String> getIndepententLines() {
        final List<String> lines = new ArrayList<>();
        for (Region region : getRegions()) {
            for (Line line : region.getLines()) {
                final StringBuilder lineBuilder = new StringBuilder();
                for (Word word : line.getWords()) {
                    lineBuilder.append(word.getText()).append(" ");
                }
                lines.add(lineBuilder.toString());
            }
        }
        return lines;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
