package com.xieaoran.netease.translator;

import com.xieaoran.netease.data.UserData;
import com.xieaoran.netease.persistence.entity.User;

public class UserTranslator {
    public static UserData translate(User user) {
        if (null == user) return null;
        return UserData.builder()
                .id(user.getId())
                .loginName(user.getLoginName())
                .nickName(user.getNickName())
                .role(user.getRole())
                .build();
    }
}
