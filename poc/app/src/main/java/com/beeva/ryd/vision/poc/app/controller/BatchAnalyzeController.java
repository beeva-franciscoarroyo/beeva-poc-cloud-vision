package com.beeva.ryd.vision.poc.app.controller;

import com.beeva.ryd.vision.poc.app.controller.bean.AnalyzeBean;
import com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor.AnalysisConfiguration;
import com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor.AnalysisExecutor;
import com.beeva.ryd.vision.poc.app.service.imageLoader.Image;
import com.beeva.ryd.vision.poc.app.service.imageLoader.ImageLoader;
import com.beeva.ryd.vision.poc.app.service.requestor.Result;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/batch_analyze")
public class BatchAnalyzeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String fileOfPaths;

    private final AnalysisExecutor analysisExecutor;

    private final ImageLoader imageLoader;

    @Autowired
    public BatchAnalyzeController(@Value("${analyze.allowed.image.conf.file}")String fileOfPaths,
                                  AnalysisExecutor analysisExecutor,
                                  ImageLoader imageLoader) {
        this.fileOfPaths = fileOfPaths;
        this.analysisExecutor = analysisExecutor;
        this.imageLoader = imageLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        getAllowedPathList().addAll(IOUtils.readLines(new FileReader(fileOfPaths)));
    }

    private List<String> getAllowedPathList() {
        try {
            return IOUtils.readLines(new FileReader(fileOfPaths));
        } catch (IOException e) {
            logger.error("Error returning list of allowed paths");
            return Collections.emptyList();
        }
    }

    @RequestMapping
    public ModelAndView analyze() {
        final ModelAndView modelAndView = new ModelAndView("batch_analyze");
        modelAndView.addObject("allowedBasePathList", getAllowedPathList());
        modelAndView.addObject("analyzeBean", new AnalyzeBean());
        modelAndView.addObject("analyzeStarted", false);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView startAnalysis(Model model, @ModelAttribute("analyzeBean")AnalyzeBean bean) {
        logger.info("Starting new analysis: " + bean.toString());
        checkValidPath(bean.getBasePathSelection());

        final List<Image> imageList = imageLoader.list(bean.getBasePathSelection());
        final VisionRequestor.RequestType requestType = getRequestType(bean);
        final AnalysisConfiguration analysisConfiguration = new AnalysisConfiguration(bean.getName(), imageList, requestType);
        CompletableFuture<List<Result>> analyze = analysisExecutor.analyze(analysisConfiguration);

        model.addAttribute("analyzeStarted", true);
        model.addAttribute("allowedBasePathList", getAllowedPathList());
        return new ModelAndView("batch_analyze", model.asMap());
    }

    private void checkValidPath(String basePathSelection) {
        getAllowedPathList().stream()
                .filter(path -> path.equalsIgnoreCase(basePathSelection))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(basePathSelection + " is not allowed"));
    }

    private VisionRequestor.RequestType getRequestType(AnalyzeBean bean) {
        switch (bean.getTypeSelection()) {
            case "Ocr Detection" :
                return VisionRequestor.RequestType.OCR_DETECTION;
            case "Label Detection" :
                return  VisionRequestor.RequestType.LABEL_DETECTION;
        }
        throw new IllegalArgumentException("TypeSelection not supported: " + bean.getTypeSelection());
    }

}
