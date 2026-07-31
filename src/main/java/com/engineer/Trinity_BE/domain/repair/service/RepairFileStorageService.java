package com.engineer.Trinity_BE.domain.repair.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RepairFileStorageService {

    @Value("${file.upload-path}")
    private String uploadPath;

    public String store(MultipartFile file) throws IOException {
        Path directory = Paths.get(uploadPath);

        if(Files.notExists(directory)) Files.createDirectories(directory);

        String originalName = file.getOriginalFilename();
        String extension = getExtension(originalName);
        String storedName = UUID.randomUUID() + extension;
        Path target = directory.resolve(storedName);
        file.transferTo(target);

        return storedName;
    }

    public Resource load (String storedName) throws MalformedURLException {
        Path file = Paths.get(uploadPath)
                .toAbsolutePath()
                .normalize()
                .resolve(storedName);

        Resource resource = new UrlResource(file.toUri());

        if(!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("파일을 찾을 수 없습니다.");
        }

        return resource;
    }

    public void delete(String storedName) throws IOException {
        Path file = Paths.get(uploadPath)
                .toAbsolutePath()
                .normalize()
                .resolve(storedName);

        Files.deleteIfExists(file);
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }

        return filename.substring(filename.lastIndexOf("."));
    }
}
