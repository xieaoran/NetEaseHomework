package com.xieaoran.netease.service;

import com.xieaoran.netease.data.ProductBasicData;
import com.xieaoran.netease.data.ProductDetailData;
import com.xieaoran.netease.enums.SessionKey;
import com.xieaoran.netease.enums.UserRole;
import com.xieaoran.netease.exception.AppException;
import com.xieaoran.netease.exception.ErrorCode;
import com.xieaoran.netease.persistence.converter.ProductConverter;
import com.xieaoran.netease.persistence.entity.*;
import com.xieaoran.netease.persistence.repository.ProductRepository;
import com.xieaoran.netease.persistence.repository.ProductSnapshotRepository;
import com.xieaoran.netease.persistence.repository.PurchasedItemRepository;
import com.xieaoran.netease.persistence.repository.ShopCartItemRepository;
import com.xieaoran.netease.request.product.ProductUpdateRequest;
import com.xieaoran.netease.translator.ProductTranslator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductSnapshotRepository productSnapshotRepository;
    private PurchasedItemRepository purchasedItemRepository;
    private ShopCartItemRepository shopCartItemRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductBasicData> list(boolean includeDeleted, Pageable pageable, HttpSession session) {
        Page<Product> products;
        if (includeDeleted) products = productRepository.findAll(pageable);
        else products = productRepository.findAllByDeletedIsFalse(pageable);

        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());

        return products.map(product -> {
            int sumQuantity = sumQuantity(product, userObj);
            return ProductTranslator.translateBasic(product, sumQuantity);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductBasicData> listNew(boolean includeDeleted, Pageable pageable, HttpSession session) {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        if (user.getRole() == UserRole.SELLER)
            throw new AppException(ErrorCode.USER_INSUFFICIENT_PERMISSION);

        Page<Product> products;
        if (includeDeleted) products = productRepository.findByUserNotPurchased(user.getId(), pageable);
        else products = productRepository.findByUserNotPurchasedAndDeletedIsFalse(user.getId(), pageable);

        return products.map(product -> {
            int sumQuantity = sumQuantity(product, userObj);
            return ProductTranslator.translateBasic(product, sumQuantity);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailData detail(int id, HttpSession session) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
        Product product = productOptional.get();

        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        int sumQuantity = sumQuantity(product, userObj);

        return ProductTranslator.translateDetail(product, sumQuantity);
    }

    @Override
    @Transactional
    public ProductDetailData update(ProductUpdateRequest request, HttpSession session) {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        if (user.getRole() == UserRole.BUYER)
            throw new AppException(ErrorCode.USER_INSUFFICIENT_PERMISSION);

        Product product;
        boolean needsUpdate;

        if (null == request.getId()) {
            product = new Product();
            needsUpdate = true;
        } else {
            Optional<Product> productOptional = productRepository.findById(request.getId());
            if (!productOptional.isPresent()) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
            product = productOptional.get();
            if (product.isDeleted()) throw new AppException(ErrorCode.PRODUCT_DELETED);

            needsUpdate = request.checkUpdate(product);
            if (needsUpdate) {
                ProductSnapshot snapshot = ProductConverter.toSnapshot(product);
                snapshot.setId(productSnapshotRepository.findMaxId(product.getId()) + 1);
                productSnapshotRepository.save(snapshot);

                Collection<PurchasedItem> purchasedItems =
                        purchasedItemRepository.findAllByProductAndProductSnapshotIdIsNull(product);
                for (PurchasedItem purchasedItem : purchasedItems) {
                    purchasedItem.setProductSnapshotId(snapshot.getId());
                }
                purchasedItemRepository.saveAll(purchasedItems);

                Collection<ShopCartItem> shopCartItems =
                        shopCartItemRepository.findAllByProductAndProductSnapshotIdIsNull(product);
                for (ShopCartItem shopCartItem : shopCartItems) {
                    shopCartItem.setProductSnapshotId(snapshot.getId());
                }
                shopCartItemRepository.saveAll(shopCartItems);
            }
        }

        if (needsUpdate) {
            request.update(product);
            productRepository.save(product);
        }

        return ProductTranslator.translateDetail(product);
    }

    @Override
    @Transactional
    public ProductBasicData delete(int id, HttpSession session) {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        if (user.getRole() == UserRole.BUYER)
            throw new AppException(ErrorCode.USER_INSUFFICIENT_PERMISSION);

        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
        Product product = productOptional.get();
        if (product.isDeleted()) throw new AppException(ErrorCode.PRODUCT_DELETED);

        product.setDeleted(true);
        productRepository.save(product);

        return ProductTranslator.translateBasic(product);
    }

    private int sumQuantity(Product product, Object userObj) {
        if (userObj instanceof User) {
            User user = (User) userObj;
            if (user.getRole() == UserRole.BUYER)
                return purchasedItemRepository.sumQuantityByUserIdAndProductId(user.getId(), product.getId());
            else return purchasedItemRepository.sumQuantityByProductId(product.getId());
        } else return 0;
    }
}
