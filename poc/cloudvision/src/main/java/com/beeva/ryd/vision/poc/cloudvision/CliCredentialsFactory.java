package com.beeva.ryd.vision.poc.cloudvision;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.vision.v1.VisionScopes;

import java.io.*;
import java.nio.file.Path;

public class CliCredentialsFactory implements CredentialsFactory {

    private final Path keyFile;

    public CliCredentialsFactory(Path keyFile) {
        this.keyFile = keyFile;
    }

    @Override
    public Credential create()  {
        final GoogleCredential credential;
        try {
            credential = GoogleCredential
                    .fromStream(getClientKeyStream())
                    .createScoped(VisionScopes.all());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Credentials json file not found");
        }
        return credential;

    }

    private InputStream getClientKeyStream() throws FileNotFoundException {
        return new FileInputStream(keyFile.toFile());
    }

}
