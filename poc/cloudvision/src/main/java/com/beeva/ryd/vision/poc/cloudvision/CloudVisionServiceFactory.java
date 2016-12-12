package com.beeva.ryd.vision.poc.cloudvision;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;

import java.io.IOException;
import java.security.GeneralSecurityException;

class CloudVisionServiceFactory {

    private static final String APPLICATION_NAME = "poc-cloud-vision.appspot.com";

    public static Vision create(CredentialsFactory credentialsFactory) {
        try {
            return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport()
                        , JacksonFactory.getDefaultInstance()
                        , credentialsFactory.create())
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException|IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create CloudVision service");
        }
    }
}
