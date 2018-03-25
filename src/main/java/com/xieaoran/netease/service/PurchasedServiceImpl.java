package com.xieaoran.netease.service;

import com.xieaoran.netease.data.PurchasedItemData;
import com.xieaoran.netease.enums.SessionKey;
import com.xieaoran.netease.enums.UserRole;
import com.xieaoran.netease.exception.AppException;
import com.xieaoran.netease.exception.ErrorCode;
import com.xieaoran.netease.persistence.entity.PurchasedItem;
import com.xieaoran.netease.persistence.entity.User;
import com.xieaoran.netease.persistence.repository.PurchasedItemRepository;
import com.xieaoran.netease.translator.PurchasedItemTranslator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PurchasedServiceImpl implements PurchasedService {
    private PurchasedItemRepository purchasedItemRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PurchasedItemData> list(boolean includeDeleted, Pageable pageable, HttpSession session) {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;

        Page<PurchasedItem> purchasedItems;
        if (user.getRole() == UserRole.BUYER)
            purchasedItems = includeDeleted ?
                    purchasedItemRepository.findAllByUser(user, pageable) :
                    purchasedItemRepository.findAllByUserAndProductDeletedIsFalse(user, pageable);
        else purchasedItems = includeDeleted ?
                purchasedItemRepository.findAll(pageable) :
                purchasedItemRepository.findAllByProductDeletedIsFalse(pageable);

        return purchasedItems.map((PurchasedItem purchasedItem) ->
                PurchasedItemTranslator.translate(purchasedItem, user.getRole() == UserRole.SELLER));
    }
}
