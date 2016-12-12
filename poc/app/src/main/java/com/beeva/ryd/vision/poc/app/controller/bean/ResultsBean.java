package com.beeva.ryd.vision.poc.app.controller.bean;

import java.util.ArrayList;
import java.util.List;

public class ResultsBean {

    private List<String> headers = new ArrayList<>();

    private List<Measure> measures = new ArrayList<>();

    private int totalImageCount = 0;

    public ResultsBean() {
        headers.add("");
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> header) {
        this.headers.addAll(header);
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public void addMeasure(Measure measure) {
        this.measures.add(measure);
    }

    public int getTotalImageCount() {
        return totalImageCount;
    }

    public void setTotalImageCount(int totalImageCount) {
        this.totalImageCount = totalImageCount;
    }
}
