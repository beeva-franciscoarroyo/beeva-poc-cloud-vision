package com.beeva.ryd.vision.poc.cloudvision;

import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.ImageSource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class ImageRequestBuilder {

    private final List<Feature> features = new ArrayList<>();

    private Image image;

    public ImageRequestBuilder addFeature(Feature feature) {
        this.features.add(feature);
        return this;
    }

    public ImageRequestBuilder addFeatures(List<Feature> features) {
        this.features.addAll(features);
        return this;
    }

    public ImageRequestBuilder addImageOnGCS(String gcsUrl) {
        final Image image = new Image();
        ImageSource source = new ImageSource();
        source.setGcsImageUri(gcsUrl);
        image.setSource(source);
        this.image = image;
        return this;
    }

    public ImageRequestBuilder addImageOnDisk(Path imagePath) throws IOException {
        final Image image = new Image();
        final FileInputStream fileInputStream = new FileInputStream(imagePath.toFile());
        final byte[] bytes = IOUtils.toByteArray(fileInputStream);
        final String imageBase64 = Base64.encodeBase64String(bytes);
        image.setContent(imageBase64);
        this.image = image;
        return this;
    }


    public AnnotateImageRequest build() {
        final AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
        annotateImageRequest.setFeatures(this.features);
        annotateImageRequest.setImage(this.image);
        return annotateImageRequest;
    }

}
