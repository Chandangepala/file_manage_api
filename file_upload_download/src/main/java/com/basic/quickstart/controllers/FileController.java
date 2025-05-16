package com.basic.quickstart.controllers;

import com.basic.quickstart.model.FileUploadResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final String UPLOAD_DIR = "uploads";

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() throws IOException {
        String fileName = "sample.pdf";
        Resource resource = new ClassPathResource("files/" + fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Ensure the directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            FileUploadResponse response = new FileUploadResponse(
                    file.getOriginalFilename(),
                    "File uploaded successfully",
                    true);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            FileUploadResponse response = new FileUploadResponse(
                    file.getOriginalFilename(),
                    "File uploaded failed!",
                    false);
            return ResponseEntity.status(500).body(response);
        }
    }
}
