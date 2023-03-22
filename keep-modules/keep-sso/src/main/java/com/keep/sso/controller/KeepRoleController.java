package com.keep.sso.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.sso.convert.KeepRoleConvert;
import com.keep.sso.entity.KeepRole;
import com.keep.sso.entity.dto.SearchDto;
import com.keep.sso.entity.vo.KeepMenuVo;
import com.keep.sso.entity.vo.KeepRoleVo;
import com.keep.sso.service.KeepRoleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author system
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/keepRoles")
@RequiredArgsConstructor
public class KeepRoleController {

    private final KeepRoleService keepRoleService;

    @ApiOperation("角色列表")
    @GetMapping("")
    public ResponseResult<PageInfo<List<KeepRoleVo>>> list(SearchDto searchDto){
        PageHelper.startPage(searchDto.getPageNum(),searchDto.getPageSize());
        List<KeepRole> keepRoles = keepRoleService.list();
        PageInfo pageInfo = new PageInfo(keepRoles);
        List<KeepRoleVo> vos = KeepRoleConvert.INSTANCE.toKeepRoleVos(keepRoles);
        pageInfo.setList(vos);
        return ResponseResult.success(pageInfo);
    }

}

