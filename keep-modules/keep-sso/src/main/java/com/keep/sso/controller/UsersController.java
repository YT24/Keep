package com.keep.sso.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.common.database.BaseEntity;
import com.keep.sso.convert.KeepUserConvert;
import com.keep.sso.entity.dto.KeepUserSaveDto;
import com.keep.sso.entity.dto.SearchDto;
import com.keep.sso.entity.vo.KeepUserVo;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.ticket.entity.KeepUser;
import com.keep.sso.ticket.service.KeepUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/keepUsers")
public class UsersController implements BaseController {

    @Autowired
    private KeepUserService keepUserService;


    @ApiOperation("用户列表")
    @GetMapping("")
    public ResponseResult<PageInfo<List<KeepUserVo>>> list(@Validated SearchDto searchDto) {
        PageHelper.startPage(searchDto.getPageNum(), searchDto.getPageSize());
        List<KeepUser> users = this.keepUserService.lambdaQuery().eq(StringUtils.isNotBlank(searchDto.getKeyword()), KeepUser::getUsername, searchDto.getKeyword()).list();
        List<KeepUserVo> vos = KeepUserConvert.INSTANCE.toKeepUserVos(users);
        PageInfo pageInfo = new PageInfo(users);
        pageInfo.setList(vos);
        return ResponseResult.success(pageInfo);
    }

    @ApiOperation("用户新建")
    @PostMapping("")
    public ResponseResult create(@RequestBody @Validated KeepUserSaveDto keepUserSaveDto) {
        this.keepUserService.save(KeepUserConvert.INSTANCE.toKeepUser(keepUserSaveDto));
        return ResponseResult.success();
    }

    @ApiOperation("用户更新")
    @PutMapping("/{id}")
    public ResponseResult update(@PathVariable Integer id,@RequestBody @Validated KeepUserSaveDto keepUserSaveDto) {
        UpdateWrapper<KeepUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(BaseEntity::getId,id);
        updateWrapper.lambda().set(KeepUser::getMobile,keepUserSaveDto.getRealName());
        updateWrapper.lambda().set(KeepUser::getMobile,keepUserSaveDto.getMobile());
        updateWrapper.lambda().set(KeepUser::getEmail,keepUserSaveDto.getEmail());
        this.keepUserService.update(updateWrapper);
        return ResponseResult.success();
    }

    @ApiOperation("用户删除")
    @DeleteMapping("/{id}")
    public ResponseResult<LoginVo> delete(@PathVariable Integer id) {
        this.keepUserService.removeById(id);
        return ResponseResult.success();
    }



}

