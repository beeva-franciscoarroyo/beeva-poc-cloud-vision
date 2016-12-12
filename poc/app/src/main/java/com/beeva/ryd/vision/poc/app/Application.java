package com.beeva.ryd.vision.poc.app;

import com.beeva.ryd.vision.poc.app.repository.Tess4JEntityRepository;
import com.beeva.ryd.vision.poc.app.service.AnalyzerExecutor.AnalysisExecutor;
import com.beeva.ryd.vision.poc.app.service.imageLoader.ImageLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final AnalysisExecutor analysisExecutor;

	@Autowired
	private Tess4JEntityRepository tess4JEntityRepository;

	@Autowired
	private ImageLoader imageLoader;

	@Autowired
	public Application(AnalysisExecutor analysisExecutor) {
		this.analysisExecutor = analysisExecutor;
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {


//		final String basePath = "/Users/fjarroyo/Documents/workspaces/iniciativas/ryd/vision/images/";
//		final List<Image> imageList = imageLoader.list(basePath);
//
//		final CompletableFuture<List<Result>> analyzeResult = analysisExecutor
//				.analyze(new AnalysisConfiguration(imageList, VisionRequestor.RequestType.OCR_DETECTION));
	}


}