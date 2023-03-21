package com.keep.sso.controller;


import com.github.pagehelper.PageInfo;
import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.sso.convert.KeepMenuConvert;
import com.keep.sso.entity.KeepMenu;
import com.keep.sso.entity.dto.KeepClientCreateDto;
import com.keep.sso.entity.dto.SearchDto;
import com.keep.sso.entity.vo.KeepCreateVo;
import com.keep.sso.entity.vo.KeepMenuVo;
import com.keep.sso.service.KeepMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author system
 * @since 2023-03-20
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/keepMenus")
@RequiredArgsConstructor
public class KeepMenuController {

    private final KeepMenuService keepMenuService;



    @ApiOperation("菜单树")
    @GetMapping("")
    public ResponseResult<PageInfo<List<KeepMenuVo>>> list(SearchDto searchDto){

        return ResponseResult.success(keepMenuService.listByPage(searchDto));
    }
}

