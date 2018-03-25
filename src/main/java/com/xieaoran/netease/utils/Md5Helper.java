package com.xieaoran.netease.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Helper {
    public static String digest(String input)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64 = new BASE64Encoder();
        byte[] bytes = md5.digest(input.getBytes("utf-8"));
        return base64.encode(bytes);
    }
}
