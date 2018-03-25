package com.xieaoran.netease.api;

import com.xieaoran.netease.data.ProductBasicData;
import com.xieaoran.netease.data.ProductDetailData;
import com.xieaoran.netease.request.product.ProductUpdateRequest;
import com.xieaoran.netease.response.GenericPageableResponse;
import com.xieaoran.netease.response.GenericSingleResponse;
import com.xieaoran.netease.service.ProductService;
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
public class ProductController {
    private ProductService productService;

    @ApiOperation(value = "获取所有商品的基本信息", notes = "无权限要求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面编号", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "页面大小", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序依据", dataType = "string", paramType = "query", defaultValue = "title")
    })
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/product")
    public GenericPageableResponse<ProductBasicData> list(
            @ApiParam(value = "是否包含已删除的商品")
            @RequestParam(value = "include_deleted", required = false, defaultValue = "false")
                    boolean includeDeleted,
            @ApiIgnore
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC)
                    Pageable pageable, HttpSession session) {
        GenericPageableResponse<ProductBasicData> response = new GenericPageableResponse<>();
        try {
            response.setPageable(productService.list(includeDeleted, pageable, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "获取未购买商品的基本信息", notes = "卖家 - 无权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面编号", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "页面大小", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序依据", dataType = "string", paramType = "query", defaultValue = "title")
    })
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/product/new")
    public GenericPageableResponse<ProductBasicData> listNew(
            @ApiParam(value = "是否包含已删除的商品")
            @RequestParam(value = "include_deleted", required = false, defaultValue = "false")
                    boolean includeDeleted,
            @ApiIgnore
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC)
                    Pageable pageable, HttpSession session) {
        GenericPageableResponse<ProductBasicData> response = new GenericPageableResponse<>();
        try {
            response.setPageable(productService.listNew(includeDeleted, pageable, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "获取指定商品的详细信息", notes = "无权限要求")
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/product/{id}")
    public GenericSingleResponse<ProductDetailData> detail(
            @ApiParam(value = "商品 ID", required = true)
            @PathVariable int id,
            HttpSession session) {
        GenericSingleResponse<ProductDetailData> response = new GenericSingleResponse<>();
        try {
            response.setItem(productService.detail(id, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "新增/更新商品", notes = "买家 - 无权限")
    @RequestMapping(method = RequestMethod.PUT, path = "api/v1/product")
    public GenericSingleResponse<ProductDetailData> update(
            @ApiParam(value = "商品请求 - 新增/更新", required = true)
            @RequestBody ProductUpdateRequest request,
            HttpSession session) {
        GenericSingleResponse<ProductDetailData> response = new GenericSingleResponse<>();
        try {
            response.setItem(productService.update(request, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "删除商品", notes = "买家 - 无权限")
    @RequestMapping(method = RequestMethod.DELETE, path = "api/v1/product/{id}")
    public GenericSingleResponse<ProductBasicData> delete(
            @ApiParam(value = "商品 ID", required = true)
            @PathVariable int id,
            HttpSession session) {
        GenericSingleResponse<ProductBasicData> response = new GenericSingleResponse<>();
        try {
            response.setItem(productService.delete(id, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }
}
