package com.fiseq.image_service.controller;

import com.fiseq.image_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException{
        return imageService.uploadImage(file);
    }

    @GetMapping("/retrieve/{fileId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long fileId) {
        return imageService.downloadImage(fileId);
    }
}
