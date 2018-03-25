package com.xieaoran.netease.api;

import com.xieaoran.netease.data.PurchasedItemData;
import com.xieaoran.netease.response.GenericPageableResponse;
import com.xieaoran.netease.service.PurchasedService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PurchasedController {
    private PurchasedService purchasedService;

    @ApiOperation(value = "获取所有已购买项目的信息",
            notes = "买家 - 自身所有已购买项目\n卖家 - 所有买家所有已购买项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面编号", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "页面大小", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序依据", dataType = "string", paramType = "query", defaultValue = "id")
    })
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/purchased")
    public GenericPageableResponse<PurchasedItemData> list(
            @ApiParam(value = "是否包含已删除的商品")
            @RequestParam(value = "include_deleted", required = false, defaultValue = "false")
                    boolean includeDeleted,
            @ApiIgnore
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                    Pageable pageable, HttpSession session) {
        GenericPageableResponse<PurchasedItemData> response = new GenericPageableResponse<>();
        try {
            response.setPageable(purchasedService.list(includeDeleted, pageable, session));
        } catch (Exception ex) {
            response.setException(ex);
        }
        return response;
    }
}
