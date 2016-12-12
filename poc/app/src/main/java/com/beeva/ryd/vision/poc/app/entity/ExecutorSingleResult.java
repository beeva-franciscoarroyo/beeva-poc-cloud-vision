package com.beeva.ryd.vision.poc.app.entity;

import com.beeva.ryd.vision.poc.app.service.requestor.CloudVisionRequestor;
import com.beeva.ryd.vision.poc.app.service.requestor.CognitiveServiceVisionRequestor;
import com.beeva.ryd.vision.poc.app.service.requestor.Tess4JVisionRequestor;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

public class ExecutorSingleResult {

    @JsonProperty("executor_name_result")
    private String exectorNameResult;

    private String executor;

    private boolean value;

    public ExecutorSingleResult() {
    }

    public String getExectorNameResult() {
        return exectorNameResult;
    }

    public void setExectorNameResult(String exectorNameResult) {
        this.exectorNameResult = exectorNameResult;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public boolean isValue() {
        return value;
    }

    public void updateName() {
        if(!StringUtils.isEmpty(exectorNameResult)) {
            if(exectorNameResult.startsWith("t4j")) {
                this.executor = Tess4JVisionRequestor.class.getSimpleName();
            } else if (exectorNameResult.startsWith("cv")){
                this.executor = CloudVisionRequestor.class.getSimpleName();
            } else if (exectorNameResult.startsWith("cgs")){
                this.executor = CognitiveServiceVisionRequestor.class.getSimpleName();
            }
        }
    }
}
