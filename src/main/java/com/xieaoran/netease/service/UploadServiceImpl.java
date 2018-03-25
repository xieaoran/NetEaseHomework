package com.xieaoran.netease.service;

import com.xieaoran.netease.data.UploadItemData;
import com.xieaoran.netease.enums.SessionKey;
import com.xieaoran.netease.enums.UserRole;
import com.xieaoran.netease.exception.AppException;
import com.xieaoran.netease.exception.ErrorCode;
import com.xieaoran.netease.persistence.entity.User;
import com.xieaoran.netease.translator.UploadItemTranslator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${com.xieaoran.netease.upload-root}")
    private String uploadRoot;

    @Value("${com.xieaoran.netease.upload-folder}")
    private String uploadFolder;

    public UploadItemData upload(MultipartFile file, HttpSession session) throws IOException {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        if (user.getRole() == UserRole.BUYER)
            throw new AppException(ErrorCode.USER_INSUFFICIENT_PERMISSION);
        if (!file.getContentType().startsWith("image"))
            throw new AppException(ErrorCode.CONTENT_TYPE_NOT_ALLOWED);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String timestamp = dateFormat.format(new Date());


        Path realUploadPath = Paths.get(uploadRoot, uploadFolder, timestamp);
        File realUploadPathFile = new File(realUploadPath.toString());
        if (!realUploadPathFile.exists()) realUploadPathFile.mkdirs();

        Path localFilePath = Paths.get(uploadRoot, uploadFolder, timestamp, file.getOriginalFilename());
        File localFile = new File(localFilePath.toString());
        file.transferTo(localFile);

        UploadItemData data = UploadItemTranslator.translate(file);
        data.setCompleteUrl(String.format("/%s/%s/%s", uploadFolder, timestamp, file.getOriginalFilename()));
        return data;
    }
}
