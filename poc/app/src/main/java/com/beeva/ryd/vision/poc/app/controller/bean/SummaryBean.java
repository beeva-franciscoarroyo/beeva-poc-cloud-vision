package com.beeva.ryd.vision.poc.app.controller.bean;

public class SummaryBean {

    private String mostPrecise = "";

    private String mostComplete = "";

    private String executorsResult = "";

    public SummaryBean() {
    }

    public String getMostPrecise() {
        return mostPrecise;
    }

    public void setMostPrecise(String mostPrecise) {
        this.mostPrecise = mostPrecise;
    }

    public String getMostComplete() {
        return mostComplete;
    }

    public void setMostComplete(String mostComplete) {
        this.mostComplete = mostComplete;
    }

    public String getExecutorsResult() {
        return executorsResult;
    }

    public void setExecutorsResult(String executorsResult) {
        this.executorsResult = executorsResult;
    }
}
