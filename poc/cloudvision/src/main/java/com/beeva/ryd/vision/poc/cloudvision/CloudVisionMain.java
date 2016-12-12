package com.beeva.ryd.vision.poc.cloudvision;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CloudVisionMain {

    public static void main(String[] args) throws IOException {
        if(args.length < 3) {
            System.err.println("Number of arguments invalid");
            System.err.println("command -Ppath1 -Ppath2 -FTEXT-DETECTION -FLABEL-DETECTION -Kkeyfile");
            System.err.println("Expample:\n\n\t-P/vision/images/logoBBVA.png -FTEXT-DETECTION -K/credentials/poc-cloud.json");
            System.exit(1);
        }

        final List<Path> images = getImagePaths(args);
        final List<String> features = getFeatures(args);
        final Path cloudVisionKeyPath = getKeyPath(args);

        final CloudVision cloudVision = new CloudVision(cloudVisionKeyPath);
        cloudVision.analyze(images, features);

    }

    private static Path getKeyPath(String[] args) {
        final Optional<String> key = Arrays.stream(args)
                .filter((path) -> path.startsWith("-K"))
                .map((path -> path.replace("-K","").trim()))
                .findFirst();

        if(!key.isPresent()) {
            throw new IllegalArgumentException("-K parameter is not received");
        }

        return Paths.get(key.get());
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
