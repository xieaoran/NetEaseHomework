package com.xieaoran.netease.service;

import com.xieaoran.netease.data.UserData;
import com.xieaoran.netease.request.user.LoginRequest;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

public interface UserService {
    UserData login(LoginRequest request, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    UserData logout(HttpSession session);

    UserData current(HttpSession session);

    Collection<UserData> addUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
