package com.keep.common.core.domain.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 基础查询分页
 * @author: 杨特
 * @Date: 2023/1/6/12:35 下午
 */
public class BaseQuery {

    @ApiModelProperty("页数（默认1）")
    private Integer pageNum = 1;

    @ApiModelProperty("每页数量（默认30）")
    private Integer pageSize = 30;

    public BaseQuery() {
    }

    public BaseQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
