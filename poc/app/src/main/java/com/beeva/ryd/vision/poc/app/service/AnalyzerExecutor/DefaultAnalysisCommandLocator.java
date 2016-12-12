package com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor;

import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultAnalysisCommandLocator implements AnalysisCommandLocator {

    private final List<AnalysisCommand> commands;

    @Autowired
    public DefaultAnalysisCommandLocator(List<AnalysisCommand> commands) {
        this.commands = commands;
    }

    @Override
    public List<AnalysisCommand> getCommandsFor(VisionRequestor.RequestType requestType) {
        return commands.stream()
                .filter(command -> command.supportedDetections().contains(requestType))
                .collect(Collectors.toList());
    }
}
