package com.xieaoran.netease.service;

import com.xieaoran.netease.data.PurchasedItemData;
import com.xieaoran.netease.data.ShopCartItemData;
import com.xieaoran.netease.request.shopcart.ShopCartUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.Collection;

public interface ShopCartService {
    Page<ShopCartItemData> list(Pageable pageable, HttpSession session);

    ShopCartItemData update(ShopCartUpdateRequest request, HttpSession session);

    ShopCartItemData delete(int productId, HttpSession session);

    Collection<PurchasedItemData> checkOut(HttpSession session);
}
