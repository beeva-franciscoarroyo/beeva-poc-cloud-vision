package com.beeva.ryd.vision.poc.tess4j;

import net.sourceforge.tess4j.Tesseract;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Tess4J {

    public static final String RESULT_ERROR = "error";

    private final Tesseract tesseract;

    /**
     * Needs
     * apt-get install tesseract ¿¿??
     * or
     * brew install tesseract
     */
    public Tess4J() {
        this.tesseract = new Tesseract();
    }

    public Tess4JAnalyzeResult analyze(List<Path> imagePathList) {
        final List<Tess4JImageResult> results = new ArrayList<>(imagePathList.size());
        for (Path imagePath : imagePathList) {
            try {
                final String result = tesseract.doOCR(imagePath.toFile());
                results.add(new Tess4JImageResult(imagePath, result));
            } catch (Exception e) {
                System.err.println(imagePath + " error: " + e.getMessage());
                Tess4JImageResult tess4JImageResult = new Tess4JImageResult(imagePath, RESULT_ERROR);
                tess4JImageResult.setError(e.getMessage());
                results.add(tess4JImageResult);
            }
        }

        return new Tess4JAnalyzeResult(results);
    }

}
