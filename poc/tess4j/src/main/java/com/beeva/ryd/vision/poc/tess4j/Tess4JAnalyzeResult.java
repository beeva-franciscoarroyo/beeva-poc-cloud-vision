package com.beeva.ryd.vision.poc.tess4j;

import java.util.ArrayList;
import java.util.List;

public class Tess4JAnalyzeResult {

    private final List<Tess4JImageResult> imageResultList = new ArrayList<>();

    public Tess4JAnalyzeResult(List<Tess4JImageResult> tess4JImageResults) {
        if(tess4JImageResults == null) {
            throw new NullPointerException("tess4JImageResults can not be null");
        }
        this.imageResultList.addAll(tess4JImageResults);
    }

    public List<Tess4JImageResult> getImageResultList() {
        return imageResultList;
    }
}
