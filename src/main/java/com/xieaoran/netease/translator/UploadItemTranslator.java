package com.xieaoran.netease.translator;

import com.xieaoran.netease.data.UploadItemData;
import org.springframework.web.multipart.MultipartFile;

public class UploadItemTranslator {
    public static UploadItemData translate(MultipartFile file) {
        if (null == file) return null;
        return UploadItemData.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
    }
}
