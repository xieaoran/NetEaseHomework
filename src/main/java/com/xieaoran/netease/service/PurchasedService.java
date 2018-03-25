package com.xieaoran.netease.service;

import com.xieaoran.netease.data.PurchasedItemData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface PurchasedService {
    Page<PurchasedItemData> list(boolean includeDeleted, Pageable pageable, HttpSession session);
}
