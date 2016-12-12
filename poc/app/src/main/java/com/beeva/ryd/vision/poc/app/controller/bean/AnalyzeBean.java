package com.beeva.ryd.vision.poc.app.controller.bean;

public class AnalyzeBean {

    private String typeSelection;

    private String basePathSelection;

    private String name;

    public AnalyzeBean() {
    }

    public String getTypeSelection() {
        return typeSelection;
    }

    public void setTypeSelection(String typeSelection) {
        this.typeSelection = typeSelection;
    }

    public String getBasePathSelection() {
        return basePathSelection;
    }

    public void setBasePathSelection(String basePathSelection) {
        this.basePathSelection = basePathSelection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AnalyzeBean{" +
                "typeSelection='" + typeSelection + '\'' +
                ", basePathSelection='" + basePathSelection + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
