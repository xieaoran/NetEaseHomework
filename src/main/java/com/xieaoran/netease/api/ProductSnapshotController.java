package com.xieaoran.netease.api;

import com.xieaoran.netease.data.ProductSnapshotBasicData;
import com.xieaoran.netease.data.ProductSnapshotDetailData;
import com.xieaoran.netease.response.GenericPageableResponse;
import com.xieaoran.netease.response.GenericSingleResponse;
import com.xieaoran.netease.service.ProductSnapshotService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductSnapshotController {
    private ProductSnapshotService productSnapshotService;

    @ApiOperation(value = "获取指定商品所有快照的基本信息", notes = "无权限要求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面编号", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "页面大小", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序依据", dataType = "string", paramType = "query", defaultValue = "id")
    })
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/product/{productId}/snapshot")
    public GenericPageableResponse<ProductSnapshotBasicData> list(
            @ApiParam(value = "商品 ID", required = true)
            @PathVariable int productId,
            @ApiIgnore
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                    Pageable pageable) {
        GenericPageableResponse<ProductSnapshotBasicData> response = new GenericPageableResponse<>();
        try {
            response.setPageable(productSnapshotService.list(productId, pageable));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }

    @ApiOperation(value = "获取指定商品指定快照的详细信息", notes = "无权限要求")
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/product/{productId}/snapshot/{id}")
    public GenericSingleResponse<ProductSnapshotDetailData> detail(
            @ApiParam(value = "商品 ID", required = true)
            @PathVariable int productId,
            @ApiParam(value = "商品快照 ID", required = true)
            @PathVariable int id) {
        GenericSingleResponse<ProductSnapshotDetailData> response = new GenericSingleResponse<>();
        try {
            response.setItem(productSnapshotService.detail(productId, id));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }
}
