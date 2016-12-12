package com.beeva.ryd.vision.poc.tess4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tess4JMain {

    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println("Number of arguments invalid");
            System.err.println("command -Ppath1 -Ppath2");
            System.err.println("Expample:\n\n\t-P/vision/images/logoBBVA.png");
            System.exit(1);
        }

        final Tess4J tess4J = new Tess4J();
        tess4J.analyze(getImagePaths(args));
    }

    private static List<Path> getImagePaths(String[] args) {
        return Arrays.stream(args)
                .filter((path -> path.startsWith("-P")))
                .map((path -> path.replace("-P","").trim()))
                .map((path -> Paths.get(path)))
                .collect(Collectors.toList());
    }

    private static List<String> getFeatures(String[] args) {
        return Arrays.stream(args)
                .filter((path -> path.startsWith("-F")))
                .map((path -> path.replace("-F","").trim()))
                .collect(Collectors.toList());
    }
}
