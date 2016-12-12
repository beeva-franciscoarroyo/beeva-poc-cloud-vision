package com.beeva.ryd.vision.poc.app.controller;

import com.beeva.ryd.vision.poc.app.service.resultsextractor.DefaultResultsExtractor;
import com.beeva.ryd.vision.poc.app.service.requestor.VisionRequestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/results")
public class ResultsController {

    private final DefaultResultsExtractor defaultResultsExtractor;

    @Autowired
    public  ResultsController(DefaultResultsExtractor defaultResultsExtractor) {
        this.defaultResultsExtractor = defaultResultsExtractor;
    }

    @RequestMapping
    public ModelAndView get() {
        final ModelAndView modelAndView = new ModelAndView("results");

        modelAndView.addObject("ocrResults", defaultResultsExtractor.getResult(VisionRequestor.RequestType.OCR_DETECTION));
        modelAndView.addObject("labelResults", defaultResultsExtractor.getResult(VisionRequestor.RequestType.LABEL_DETECTION));
        return modelAndView;
    }


}
