package com.beeva.ryd.vision.poc.cloudvision;

import com.google.api.services.vision.v1.model.EntityAnnotation;

public class CloudVisionLabelDetectionResult {

    private Float confidence;

    private String description;

    private String locale;

    private String mid;

    private Float score;

    private Float topicality;

    public CloudVisionLabelDetectionResult() {
    }

    public CloudVisionLabelDetectionResult(EntityAnnotation entityAnnotation) {
        this.confidence = entityAnnotation.getConfidence();
        this.description = entityAnnotation.getDescription();
        this.locale = entityAnnotation.getLocale();
        this.mid = entityAnnotation.getMid();
        this.score = entityAnnotation.getScore();
        this.topicality = entityAnnotation.getTopicality();
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getTopicality() {
        return topicality;
    }

    public void setTopicality(Float topicality) {
        this.topicality = topicality;
    }
}
