package com.beeva.ryd.vision.poc.app.entity;

import org.springframework.data.annotation.Id;

public class ImageEntity {

    @Id
    private String name;

    private String path;

    public ImageEntity(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
