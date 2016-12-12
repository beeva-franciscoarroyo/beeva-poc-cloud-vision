package com.beeva.ryd.vision.poc.cognitiveservices;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class CognitiveServicesMain {

    public static void main(String[] args) throws IOException {
        if(args.length < 3) {
            System.err.println("Number of arguments invalid");
            System.err.println("command -Ppath -FTags -Kkey");
            System.err.println("Expample:\n\n\t-P/vision/images/logoBBVA.png -FTags -K123123123");
            System.exit(1);
        }

        final Path imagePath = getImagePath(args);
        final String feature = getFeature(args);
        final String key = getKey(args);

        final CognitiveServicesService cognitiveService = new CognitiveServicesService(key);
        cognitiveService.analyze(Collections.singletonList(imagePath), feature);
    }

    private static String getKey(String[] args) {
        return ArgumentsHelper.getFirstArgumentsOfMandatoryOption(args, "-K");
    }

    private static String getFeature(String[] args) {
        return ArgumentsHelper.getFirstArgumentsOfMandatoryOption(args, "-F");
    }

    private static Path getImagePath(String[] args) {
        return ArgumentsHelper.getFirstArgumentsOfMandatoryOption(args, "-P", Paths::get);
    }


}
