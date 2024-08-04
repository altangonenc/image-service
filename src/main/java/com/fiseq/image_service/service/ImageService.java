package com.fiseq.image_service.service;

import com.fiseq.image_service.constants.Status;
import com.fiseq.image_service.dto.ImageResponseDto;
import com.fiseq.image_service.entity.Image;
import com.fiseq.image_service.exception.NoSuchImageExistException;
import com.fiseq.image_service.repository.ImageRepository;
import com.fiseq.image_service.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public ResponseEntity<?> uploadImage(MultipartFile imageFile) throws IOException {
        if (imageFile != null)
            throw new NoSuchImageExistException("The image you tried to upload was not found.");
        try {
            var imageToSave = Image.builder()
                    .name(imageFile.getOriginalFilename())
                    .type(imageFile.getContentType())
                    .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                    .build();
            imageRepository.save(imageToSave);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ImageResponseDto(imageFile.getOriginalFilename(),imageToSave.getId(), Status.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ImageResponseDto(Status.FAILURE, e.getMessage()));
        }
    }

    @Transactional()
    public byte[] downloadImage(Long fileId) {
        Optional<Image> dbImage = imageRepository.findById(fileId);
        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID", image.getId())
                        .addContextValue("Image name", image.getName());
            }
        }).orElse(null);
    }
}
