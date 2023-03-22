package com.keep.sso.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.common.database.BaseEntity;
import com.keep.sso.convert.KeepRoleConvert;
import com.keep.sso.entity.KeepRole;
import com.keep.sso.entity.dto.KeepRoleSaveDto;
import com.keep.sso.entity.dto.SearchDto;
import com.keep.sso.entity.vo.KeepRoleVo;
import com.keep.sso.service.KeepRoleService;
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
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/keepRoles")
@RequiredArgsConstructor
public class KeepRoleController {

    private final KeepRoleService keepRoleService;

    @ApiOperation("角色列表")
    @GetMapping("")
    public ResponseResult<PageInfo<List<KeepRoleVo>>> list(@Validated SearchDto searchDto){
        PageHelper.startPage(searchDto.getPageNum(),searchDto.getPageSize());
        List<KeepRole> keepRoles = keepRoleService.list();
        PageInfo pageInfo = new PageInfo(keepRoles);
        List<KeepRoleVo> vos = KeepRoleConvert.INSTANCE.toKeepRoleVos(keepRoles);
        pageInfo.setList(vos);
        return ResponseResult.success(pageInfo);
    }

    @ApiOperation("角色新增")
    @PostMapping("")
    public ResponseResult create(@RequestBody @Validated KeepRoleSaveDto saveDto){
        this.keepRoleService.save(KeepRoleConvert.INSTANCE.toKeepRole(saveDto));
        return ResponseResult.success();
    }

    @ApiOperation("角色更新")
    @PutMapping("/{id}")
    public ResponseResult update(@PathVariable Integer id,@RequestBody KeepRoleSaveDto saveDto){
        UpdateWrapper<KeepRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(BaseEntity::getId,id);
        updateWrapper.lambda().set(KeepRole::getName,saveDto.getName());
        this.keepRoleService.update(updateWrapper);
        return ResponseResult.success();
    }

    @ApiOperation("角色删除")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Integer id){
        this.keepRoleService.removeById(id);
        return ResponseResult.success();
    }
}

