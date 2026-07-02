package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepairFileResponse {

    private Long fileId;
    private String originalName;
    private String storedName;
    private String filePath;
    private String extension;
    private String mimeType;
    private Long size;
}
