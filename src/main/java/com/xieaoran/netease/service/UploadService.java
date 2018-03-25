package com.xieaoran.netease.service;

import com.xieaoran.netease.data.UploadItemData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface UploadService {
    UploadItemData upload(MultipartFile file, HttpSession session) throws IOException;
}
