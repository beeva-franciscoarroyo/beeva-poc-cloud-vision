package com.beeva.ryd.vision.poc.cognitiveservices;

import com.beeva.ryd.vision.poc.cognitiveservices.bean.AnalyzeResponse;
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

public class AnalyzeTagsRequest {

    private static final String API_BASE_URL = "https://api.projectoxford.ai/vision/v1.0";

    private static final String API_KEY_HEADER = "Ocp-Apim-Subscription-Key";

    private final String apiKey;

    private final Client client;

    public AnalyzeTagsRequest(String apiKey) {
        this.apiKey = apiKey;
        this.client = ClientBuilder.newClient().register(JacksonFeature.class);
    }

    public CognitiveServiceAnalyzeResult analyze(List<Path> imagePathList)  {

        final List<AnalyzeResponse> analyzeResponses = new ArrayList<>(imagePathList.size());
        for (Path path : imagePathList) {
            byte[] body = getBodyFromPath(path);

            if(body == null) {
                final AnalyzeResponse response = new AnalyzeResponse();
                response.setName(path.getFileName().toString());
                response.setPath(path);
                analyzeResponses.add(response);
            } else {
                final Entity<byte[]> entity = Entity.entity(body, "application/octet-stream");
                AnalyzeResponse response;
                try {
                    response = client.target(API_BASE_URL)
                            .path("analyze")
                            .queryParam("visualFeatures", "Tags")
                            .request()
                            .header(API_KEY_HEADER, apiKey)
                            .post(entity, AnalyzeResponse.class);
                }catch (BadRequestException badRequestException){
                    response = new AnalyzeResponse();
                    response.setError(badRequestException.getMessage());
                }
                response.setName(path.getFileName().toString());
                response.setPath(path);
                analyzeResponses.add(response);
            }
        }

        return new CognitiveServiceAnalyzeResult(analyzeResponses, null);
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