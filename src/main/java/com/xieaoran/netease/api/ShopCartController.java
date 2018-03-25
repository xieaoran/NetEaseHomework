package com.xieaoran.netease.api;

import com.xieaoran.netease.data.PurchasedItemData;
import com.xieaoran.netease.data.ShopCartItemData;
import com.xieaoran.netease.request.shopcart.ShopCartUpdateRequest;
import com.xieaoran.netease.response.GenericMultipleResponse;
import com.xieaoran.netease.response.GenericPageableResponse;
import com.xieaoran.netease.response.GenericSingleResponse;
import com.xieaoran.netease.service.ShopCartService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShopCartController {
    private ShopCartService shopCartService;

    @ApiOperation(value = "获取所有购物车项目的信息",
            notes = "买家 - 自身所有购物车项目\n卖家 - 所有买家所有购物车项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面编号", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "页面大小", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序依据", dataType = "string", paramType = "query", defaultValue = "id")
    })
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/shop_cart")
    public GenericPageableResponse<ShopCartItemData> list(
            @ApiIgnore
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                    Pageable pageable, HttpSession session) {
        GenericPageableResponse<ShopCartItemData> response = new GenericPageableResponse<>();
        try {
            response.setPageable(shopCartService.list(pageable, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "新增/更新购物车项目", notes = "卖家 - 无权限")
    @RequestMapping(method = RequestMethod.PUT, path = "api/v1/shop_cart")
    public GenericSingleResponse<ShopCartItemData> update(
            @ApiParam(value = "购物车项目请求 - 新增/更新", required = true)
            @RequestBody ShopCartUpdateRequest request,
            HttpSession session) {
        GenericSingleResponse<ShopCartItemData> response = new GenericSingleResponse<>();
        try {
            response.setItem(shopCartService.update(request, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "删除购物车项目", notes = "卖家 - 无权限")
    @RequestMapping(method = RequestMethod.DELETE, path = "api/v1/shop_cart/{productId}")
    public GenericSingleResponse<ShopCartItemData> delete(
            @ApiParam(value = "商品 ID", required = true)
            @PathVariable int productId,
            HttpSession session) {
        GenericSingleResponse<ShopCartItemData> response = new GenericSingleResponse<>();
        try {
            response.setItem(shopCartService.delete(productId, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "购物车结算", notes = "卖家 - 无权限")
    @RequestMapping(method = RequestMethod.POST, path = "api/v1/shop_cart/check_out")
    public GenericMultipleResponse<PurchasedItemData> checkOut(HttpSession session) {
        GenericMultipleResponse<PurchasedItemData> response = new GenericMultipleResponse<>();
        try {
            response.setItems(shopCartService.checkOut(session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }
}
