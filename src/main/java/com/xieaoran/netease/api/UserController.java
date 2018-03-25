package com.xieaoran.netease.api;

import com.xieaoran.netease.data.UserData;
import com.xieaoran.netease.request.user.LoginRequest;
import com.xieaoran.netease.response.GenericMultipleResponse;
import com.xieaoran.netease.response.GenericSingleResponse;
import com.xieaoran.netease.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @RequestMapping(method = RequestMethod.POST, path = "api/v1/user/login")
    public GenericSingleResponse<UserData> login(
            @ApiParam(value = "用户请求 - 登录", required = true)
            @RequestBody LoginRequest request,
            HttpSession session) {
        GenericSingleResponse<UserData> response = new GenericSingleResponse<>();
        try {
            response.setItem(userService.login(request, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "用户退出")
    @RequestMapping(method = RequestMethod.POST, path = "api/v1/user/logout")
    public GenericSingleResponse<UserData> logout(HttpSession session) {
        GenericSingleResponse<UserData> response = new GenericSingleResponse<>();
        try {
            response.setItem(userService.logout(session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "获取当前用户的信息")
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/user/current")
    public GenericSingleResponse<UserData> current(HttpSession session) {
        GenericSingleResponse<UserData> response = new GenericSingleResponse<>();
        try {
            response.setItem(userService.current(session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "新增测试用户", notes = "买家 - buyer:reyub\n卖家 - seller:relles")
    @RequestMapping(method = RequestMethod.POST, path = "api/v1/user")
    public GenericMultipleResponse<UserData> addUsers() {
        GenericMultipleResponse<UserData> response = new GenericMultipleResponse<>();
        try {
            response.setItems(userService.addUsers());
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }
}
