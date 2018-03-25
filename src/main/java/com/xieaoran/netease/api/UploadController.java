package com.xieaoran.netease.api;

import com.xieaoran.netease.data.UploadItemData;
import com.xieaoran.netease.response.GenericSingleResponse;
import com.xieaoran.netease.service.UploadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UploadController {

    private UploadService uploadService;

    @ApiOperation(value = "上传文件", notes = "买家 - 无权限")
    @RequestMapping(method = RequestMethod.POST, path = "api/v1/upload")
    public GenericSingleResponse<UploadItemData> upload(
            @ApiParam(value = "所需上传的文件")
            @RequestParam("file") MultipartFile file,
            HttpSession session) {
        GenericSingleResponse<UploadItemData> response = new GenericSingleResponse<>();
        try {
            response.setItem(uploadService.upload(file, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }
}
