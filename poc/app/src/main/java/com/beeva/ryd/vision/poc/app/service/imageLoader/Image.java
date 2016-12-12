package com.beeva.ryd.vision.poc.app.service.imageLoader;

import java.nio.file.Path;

public class Image {

    private final String name;

    private final Path path;

    public Image(String name, Path path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public Path getPath() {
        return path;
    }
}
