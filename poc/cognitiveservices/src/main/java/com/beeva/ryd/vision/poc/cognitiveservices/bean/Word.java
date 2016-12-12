package com.beeva.ryd.vision.poc.cognitiveservices.bean;

public class Word {

    String boundingBox;

    String text;

    public Word() {
    }

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
