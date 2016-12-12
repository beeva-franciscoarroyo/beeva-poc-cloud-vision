package com.beeva.ryd.vision.poc.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ComparisonEntity {

    @Id
    private String id;

    private String type;

    private List<ImageComparison> images;

    private LocalDateTime created = LocalDateTime.now();

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ImageComparison> getImages() {
        return images;
    }

    public void setImages(List<ImageComparison> images) {
        this.images = images;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public ImageComparison getImageByName(String imageName) {
        return images.stream()
                .filter(imageComparison -> imageComparison.getName().equalsIgnoreCase(imageName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Imagename not found: " + imageName));
    }

    public Optional<ImageComparison> findUnComparedImage() {
        return images.stream()
                .filter(imageComparison -> StringUtils.isEmpty(imageComparison.getMostComplete())
                        && StringUtils.isEmpty(imageComparison.getMostPrecised()))
                .findFirst();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
