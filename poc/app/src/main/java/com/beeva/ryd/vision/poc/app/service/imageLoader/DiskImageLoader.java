package com.beeva.ryd.vision.poc.app.service.imageLoader;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DiskImageLoader implements ImageLoader {

    private Predicate<? super Path> onlyImagesFilter = (path) -> {
        final String filePath  = path.toString().toLowerCase();
        return filePath.endsWith("jpg")
                || filePath.endsWith("jpeg")
                || filePath.endsWith("png")
                || filePath.endsWith("bmp");
        };

    @Override
    public List<Image> list(String basePath) {
        final Path base = Paths.get(basePath);
        try {
            final Stream<Path> files = Files.list(base);
            return files
                    .filter(onlyImagesFilter)
                    .map((path -> new Image("", path)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("base directory not found", e);
        }
    }
}
