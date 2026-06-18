package com.engineer.Trinity_BE.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FileExtension {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif"),
    WEBP("webp");

    private final String mimeType;

    public static FileExtension from(String extension) {
        if (extension == null) {
            return null;
        }
        return Arrays.stream(FileExtension.values())
                .filter(e -> e.name().equalsIgnoreCase(extension))
                .findFirst()
                .orElse(null);
    }

    public static boolean isSupported(String extension) {
        return from(extension) != null;
    }

}
