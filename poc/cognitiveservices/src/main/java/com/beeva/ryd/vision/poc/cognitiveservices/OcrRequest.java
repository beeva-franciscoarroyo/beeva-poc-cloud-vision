package com.beeva.ryd.vision.poc.cognitiveservices;

import com.beeva.ryd.vision.poc.cognitiveservices.bean.OcrResponse;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OcrRequest {

    private static final String API_BASE_URL = "https://api.projectoxford.ai/vision/v1.0";

    private static final String API_KEY_HEADER = "Ocp-Apim-Subscription-Key";

    private final String apiKey;

    private final Client client;

    public OcrRequest(String apiKey) {
        this.apiKey = apiKey;
        this.client = ClientBuilder.newClient().register(JacksonFeature.class);
    }

    public CognitiveServiceAnalyzeResult analyze(List<Path> imagePathList) {

        final List<OcrResponse> ocrResponses = new ArrayList<>(imagePathList.size());
        for (Path path : imagePathList) {
            byte[] body = getBodyFromPath(path);

            if(body == null) {
                final OcrResponse response = new OcrResponse();
                response.setName(path.getFileName().toString());
                response.setPath(path);
                ocrResponses.add(response);
            } else {
                final Entity<byte[]> entity = Entity.entity(body, "application/octet-stream");
                OcrResponse response;
                try {
                    response = client.target(API_BASE_URL)
                            .path("ocr")
                            .request()
                            .header(API_KEY_HEADER, apiKey)
                            .post(entity, OcrResponse.class);  //Tags -> AnalyzeResponse
                } catch (BadRequestException badRequestException) {
                    response = new OcrResponse();
                    response.setError(badRequestException.getMessage());
                }
                response.setName(path.getFileName().toString());
                response.setPath(path);
                ocrResponses.add(response);
            }
        }

        return new CognitiveServiceAnalyzeResult(null, ocrResponses);
    }


    private byte[] getBodyFromPath(Path path)  {
        try {
            final FileInputStream fis = new FileInputStream(path.toFile());
            return IOUtils.toByteArray(fis);
        } catch (IOException e) {
            return null;
        }
    }
}