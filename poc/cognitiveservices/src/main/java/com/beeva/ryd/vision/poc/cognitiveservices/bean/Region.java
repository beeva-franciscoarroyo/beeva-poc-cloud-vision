package com.beeva.ryd.vision.poc.cognitiveservices.bean;

import java.util.ArrayList;
import java.util.List;

public class Region {

    String boundingBox;

    List<Line> lines = new ArrayList<>();

    public Region() {
    }

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
