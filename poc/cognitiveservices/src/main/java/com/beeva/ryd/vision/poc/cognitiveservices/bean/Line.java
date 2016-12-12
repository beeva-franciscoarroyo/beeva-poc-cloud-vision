package com.beeva.ryd.vision.poc.cognitiveservices.bean;

import java.util.ArrayList;
import java.util.List;

public class Line {

    String boundingBox;

    List<Word> words = new ArrayList<>();

    public Line() {
    }

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
