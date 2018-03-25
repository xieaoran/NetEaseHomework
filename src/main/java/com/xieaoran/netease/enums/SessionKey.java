package com.xieaoran.netease.enums;

import lombok.Getter;

@Getter
public enum SessionKey {
    USER_ID("user_id");
    private String key;

    SessionKey(String key) {
        this.key = key;
    }
}
