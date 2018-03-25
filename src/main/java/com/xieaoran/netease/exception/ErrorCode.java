package com.xieaoran.netease.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(""),
    USER_ALREADY_ADDED("测试用户已存在。"),
    USER_NOT_EXIST("用户不存在。"),
    USER_NOT_LOGGED_IN("用户尚未登录。"),
    USER_PASSWORD_INCORRECT("用户密码不正确。"),
    USER_INSUFFICIENT_PERMISSION("用户权限不足。"),
    PRODUCT_NOT_EXIST("商品不存在。"),
    PRODUCT_DELETED("商品已删除。"),
    PRODUCT_EXPIRED("商品已过期。"),
    PRODUCT_SNAPSHOT_NOT_EXIST("商品快照不存在。"),
    SHOP_CART_ITEM_NOT_EXIST("购物车项不存在。"),
    SHOP_CART_ITEM_QUANTITY_NON_POSITIVE("购买的商品数量应为正数。"),
    CONTENT_TYPE_NOT_ALLOWED("不允许上传的文件类型。");
    private String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
