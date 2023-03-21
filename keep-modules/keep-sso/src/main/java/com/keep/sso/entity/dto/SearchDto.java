package com.keep.sso.entity.dto;

import com.keep.common.core.domain.dto.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchDto extends BaseQuery {

    @ApiModelProperty("搜索关键字")
    private String keyword;
}
