package com.xieaoran.netease.service;

import com.xieaoran.netease.data.PurchasedItemData;
import com.xieaoran.netease.data.ShopCartItemData;
import com.xieaoran.netease.enums.SessionKey;
import com.xieaoran.netease.enums.UserRole;
import com.xieaoran.netease.exception.AppException;
import com.xieaoran.netease.exception.ErrorCode;
import com.xieaoran.netease.persistence.converter.ShopCartItemConverter;
import com.xieaoran.netease.persistence.entity.Product;
import com.xieaoran.netease.persistence.entity.PurchasedItem;
import com.xieaoran.netease.persistence.entity.ShopCartItem;
import com.xieaoran.netease.persistence.entity.User;
import com.xieaoran.netease.persistence.repository.ProductRepository;
import com.xieaoran.netease.persistence.repository.PurchasedItemRepository;
import com.xieaoran.netease.persistence.repository.ShopCartItemRepository;
import com.xieaoran.netease.request.shopcart.ShopCartUpdateRequest;
import com.xieaoran.netease.translator.PurchasedItemTranslator;
import com.xieaoran.netease.translator.ShopCartItemTranslator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShopCartServiceImpl implements ShopCartService {
    private ShopCartItemRepository shopCartItemRepository;
    private PurchasedItemRepository purchasedItemRepository;
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ShopCartItemData> list(Pageable pageable, HttpSession session) {

        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;

        Page<ShopCartItem> shopCartItems;
        if (user.getRole() == UserRole.BUYER)
            shopCartItems = shopCartItemRepository.findAllByUser(user, pageable);
        else shopCartItems = shopCartItemRepository.findAll(pageable);

        return shopCartItems.map((ShopCartItem shopCartItem) ->
                ShopCartItemTranslator.translate(shopCartItem, user.getRole() == UserRole.SELLER));
    }

    @Override
    @Transactional
    public ShopCartItemData update(ShopCartUpdateRequest request, HttpSession session) {
        if (request.getQuantity() <= 0)
            throw new AppException(ErrorCode.SHOP_CART_ITEM_QUANTITY_NON_POSITIVE);

        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        if (user.getRole() == UserRole.SELLER)
            throw new AppException(ErrorCode.USER_INSUFFICIENT_PERMISSION);

        Optional<Product> productOptional = productRepository.findById(request.getProductId());
        if (!productOptional.isPresent()) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
        Product product = productOptional.get();
        if (product.isDeleted()) throw new AppException(ErrorCode.PRODUCT_DELETED);

        Optional<ShopCartItem> shopCartItemOptional =
                shopCartItemRepository.findByUserAndProduct(user, product);
        ShopCartItem shopCartItem;

        if (shopCartItemOptional.isPresent()) {
            shopCartItem = shopCartItemOptional.get();
            if (request.checkUpdate(shopCartItem)) {
                request.update(shopCartItem);
                shopCartItemRepository.save(shopCartItem);
            }
        } else {
            shopCartItem = new ShopCartItem();
            request.update(shopCartItem);
            shopCartItem.setId(shopCartItemRepository.findMaxId(user.getId()) + 1);
            shopCartItem.setUserId(user.getId());
            shopCartItemRepository.save(shopCartItem);

            // Temporary Workaround for Cascade Initialization Failure
            shopCartItem.setUser(user);
            shopCartItem.setProduct(product);
        }
        return ShopCartItemTranslator.translate(shopCartItem, false);
    }

    @Override
    @Transactional
    public ShopCartItemData delete(int productId, HttpSession session) {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        if (user.getRole() == UserRole.SELLER)
            throw new AppException(ErrorCode.USER_INSUFFICIENT_PERMISSION);

        Optional<ShopCartItem> shopCartItemOptional =
                shopCartItemRepository.findByUserAndProductId(user, productId);
        if (!shopCartItemOptional.isPresent())
            throw new AppException(ErrorCode.SHOP_CART_ITEM_NOT_EXIST);
        ShopCartItem shopCartItem = shopCartItemOptional.get();

        ShopCartItemData data = ShopCartItemTranslator.translate(shopCartItem, false);
        shopCartItemRepository.delete(shopCartItem);
        return data;
    }

    @Override
    @Transactional
    public Collection<PurchasedItemData> checkOut(HttpSession session) {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        if (user.getRole() == UserRole.SELLER)
            throw new AppException(ErrorCode.USER_INSUFFICIENT_PERMISSION);

        Collection<ShopCartItem> shopCartItems = shopCartItemRepository.findAllByUser(user);
        Collection<PurchasedItemData> responseItems = new ArrayList<>();
        for (ShopCartItem shopCartItem : shopCartItems) {
            Product product = shopCartItem.getProduct();

            if (null != shopCartItem.getProductSnapshotId())
                throw new AppException(ErrorCode.PRODUCT_EXPIRED);
            if (product.isDeleted())
                throw new AppException(ErrorCode.PRODUCT_DELETED);

            PurchasedItem purchasedItem = ShopCartItemConverter.toPurchasedItem(shopCartItem);
            purchasedItem.setId(purchasedItemRepository.findMaxId(user.getId()) + 1);
            purchasedItemRepository.save(purchasedItem);
            shopCartItemRepository.delete(shopCartItem);

            // Temporary Workaround for Cascade Initialization Failure
            purchasedItem.setUser(user);
            purchasedItem.setProduct(product);

            responseItems.add(PurchasedItemTranslator.translate(purchasedItem, false));
        }
        return responseItems;
    }

}
