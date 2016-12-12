package com.beeva.ryd.vision.poc.cognitiveservices;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArgumentsHelper {

    public static <T> List<T> getArgumentsOfOption(String[] args, String option, Function<String, ? extends T> mapper) {
        return Arrays.stream(args)
                .filter((path -> path.startsWith(option)))
                .map((path -> path.replace(option,"").trim()))
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T> T getFirstArgumentsOfMandatoryOption(String[] args, String option, Function<String, ? extends T> mapper) {
        return Arrays.stream(args)
                .filter((path -> path.startsWith(option)))
                .map((path -> path.replace(option,"").trim()))
                .map(mapper)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("-K parameter is not received") );

    }

    public static String getFirstArgumentsOfMandatoryOption(String[] args, String option) {
        return Arrays.stream(args)
                .filter((path -> path.startsWith(option)))
                .map((path -> path.replace(option,"").trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("-K parameter is not received") );
    }

}
