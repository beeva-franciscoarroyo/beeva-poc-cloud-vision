package com.beeva.ryd.vision.poc.app.controller;

import com.beeva.ryd.vision.poc.app.controller.bean.SummaryBean;
import com.beeva.ryd.vision.poc.app.entity.ComparisonEntity;
import com.beeva.ryd.vision.poc.app.entity.ImageComparison;
import com.beeva.ryd.vision.poc.app.repository.CloudVisionEntityRepository;
import com.beeva.ryd.vision.poc.app.repository.CognitiveServiceEntityRepository;
import com.beeva.ryd.vision.poc.app.repository.ComparisonEntityRepository;
import com.beeva.ryd.vision.poc.app.repository.Tess4JEntityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.apache.commons.io.FilenameUtils.getExtension;

@Controller
@RequestMapping("comparisons")
public class ComparisonsController {

    private final ComparisonEntityRepository comparisonEntityRepository;

    private final Tess4JEntityRepository tess4JEntityRepository;

    private final CloudVisionEntityRepository cloudVisionEntityRepository;

    private final CognitiveServiceEntityRepository cognitiveServiceEntityRepository;

    @Autowired
    public ComparisonsController(ComparisonEntityRepository comparisonEntityRepository,
                                 Tess4JEntityRepository tess4JEntityRepository,
                                 CloudVisionEntityRepository cloudVisionEntityRepository,
                                 CognitiveServiceEntityRepository cognitiveServiceEntityRepository) {
        this.comparisonEntityRepository = comparisonEntityRepository;
        this.tess4JEntityRepository = tess4JEntityRepository;
        this.cloudVisionEntityRepository = cloudVisionEntityRepository;
        this.cognitiveServiceEntityRepository = cognitiveServiceEntityRepository;
    }

    @RequestMapping
    public ModelAndView list() {
        final ModelAndView modelAndView = new ModelAndView("comparisonList");


        final Sort.Order typeAscOrder = new Sort.Order(Sort.Direction.DESC, "type");
        final Sort.Order createAscOrder = new Sort.Order(Sort.Direction.ASC, "create");
        final Sort sort = new Sort(typeAscOrder, createAscOrder);
        modelAndView.addObject("comparisonList", comparisonEntityRepository.findAll(sort));
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView getById(@PathVariable("id")String id) {
        final ModelAndView modelAndView = new ModelAndView("comparison");
        modelAndView.addObject("comparison", comparisonEntityRepository.findOne(id));
        return modelAndView;
    }

    @RequestMapping("/{id}/{imageName:.+}")
    public ModelAndView getImage(@PathVariable("id")String id, @PathVariable("imageName")String imageName) throws JsonProcessingException {
        final ModelAndView modelAndView = new ModelAndView("imageComparison");
        final ComparisonEntity comparison = comparisonEntityRepository.findOne(id);
        modelAndView.addObject("comparison", comparison);
        modelAndView.addObject("imageName", imageName);
//        modelAndView.addObject("trimmedImagePath", getTrimmedImagePath(comparison, imageName));
        modelAndView.addObject("trimmedImagePath", imageName);
        modelAndView.addObject("imageContent", getImageContent(comparison, imageName));
        modelAndView.addObject("tess4jResult", tess4JEntityRepository.findByName(imageName));
        modelAndView.addObject("cloudVisionResult", cloudVisionEntityRepository.findByName(imageName));
        modelAndView.addObject("cognitiveServiceResult", cognitiveServiceEntityRepository.findByName(imageName));

        final ImageComparison imageByName = comparison.getImageByName(imageName);
        final SummaryBean summaryBean = new SummaryBean();
        summaryBean.setMostComplete(imageByName.getMostComplete());
        summaryBean.setMostPrecise(imageByName.getMostPrecised());
        summaryBean.setExecutorsResult(imageByName.getExecutorSingleResultListJSON());
        modelAndView.addObject("summaryBean", summaryBean);

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/{imageName:.+}", method = RequestMethod.POST)
    public String updateSummary(@PathVariable("id")String id,
                                      @PathVariable("imageName")String imageName,
                                      @ModelAttribute("summaryBean") SummaryBean summaryBean) throws IOException {
        final ComparisonEntity comparison = comparisonEntityRepository.findOne(id);


        ImageComparison imageByName = comparison.getImageByName(imageName);
        imageByName.setMostPrecised(summaryBean.getMostPrecise());
        imageByName.setMostComplete(summaryBean.getMostComplete());
        imageByName.setExecutorSingleResultListFromJson(summaryBean.getExecutorsResult());
        comparisonEntityRepository.save(comparison);

        Optional<ImageComparison> unComparedImage = comparison.findUnComparedImage();
        if(unComparedImage.isPresent()) {
            return "redirect:/comparisons/" + id + "/" + unComparedImage.get().getName();
        } else {
            return "redirect:/comparisons/" + id;
        }
    }

    private String getImageContent(ComparisonEntity comparison, String imageName) {
        final String imagePath = getImagePath(comparison, imageName);
        try {
            final String imageBase64 = readFromDiskOnBase64(imagePath);
            final String extension = getExtension(imagePath);
            return "data:image/" + extension + ";base64," + imageBase64;
        } catch (IOException e) {
            throw new IllegalStateException("Can not read image from disk: " + imagePath);
        }
    }

    private String readFromDiskOnBase64(String imagePath) throws IOException {
        return Base64.encodeBase64String(IOUtils.toByteArray(new FileInputStream(imagePath)));
    }

//    private String getTrimmedImagePath(ComparisonEntity comparison, String imageName) {
//        final String imagePath = getImagePath(comparison, imageName);
//        return imagePath.replace(basePath, "");
//    }

    private String getImagePath(ComparisonEntity comparison, String imageName) {
        return comparison.getImages().stream()
                .filter(imageComparison -> imageComparison.getName().contains(imageName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Image not found: " + imageName))
                .getPath();
    }
}
