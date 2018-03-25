package com.xieaoran.netease.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "通用响应 - 分页的数据集合")
public class GenericPageableResponse<T> extends GenericResponse {
    @ApiModelProperty(value = "当前页面编号")
    protected int currentPage = 0;
    @ApiModelProperty(value = "页面总数量")
    protected int totalPages = 0;
    @ApiModelProperty(value = "数据总数量")
    protected long totalCount = 0;
    @ApiModelProperty(value = "页面大小")
    protected int pageSize = 0;

    @ApiModelProperty(value = "数据集合")
    private Collection<T> items = new ArrayList<>();

    public void setPageable(Page<T> pageable) {
        if (null != pageable) {
            this.items.clear();
            this.setCurrentPage(pageable.getNumber());
            this.setTotalPages(pageable.getTotalPages());
            this.setTotalCount(pageable.getTotalElements());
            this.setPageSize(pageable.getSize());
            pageable.forEach(item -> this.items.add(item));
        }
    }

    public void setItem(T item) {
        if (null != item) {
            this.items.clear();
            this.setCurrentPage(0);
            this.setTotalPages(1);
            this.setTotalCount(1);
            this.setPageSize(1);
            this.items.add(item);
        }
    }

}
