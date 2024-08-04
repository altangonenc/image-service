package com.fiseq.image_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiseq.image_service.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
public class ImageResponseDto {
    private String fileName;
    private Long fileId;
    private Status status;
    private String error;

    public ImageResponseDto(String fileName, Long fileId, Status status) {
        this.fileName = fileName;
        this.fileId = fileId;
        this.status = status;
    }
    public ImageResponseDto(Status status, String message) {
        this.status = status;
        this.error = message;
    }
}
