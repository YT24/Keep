package com.keep.sso.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.sso.entity.KeepClient;
import com.keep.sso.entity.dto.ClientSearchDto;
import com.keep.sso.entity.dto.KeepClientCreateDto;
import com.keep.sso.entity.vo.KeepClientVo;
import com.keep.sso.entity.vo.KeepCreateVo;
import com.keep.sso.service.KeepClientsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author system
 * @since 2023-03-15
 */
@Api(tags = "单点登录应用管理")
@RestController
@RequestMapping("/keepClients")
public class KeepClientsController {

    @Autowired
    private KeepClientsService keepRegisterService;

    @ApiOperation("应用列表")
    @GetMapping("")
    public ResponseResult<PageInfo<List<KeepClientVo>>> list(@Validated ClientSearchDto searchDto){
        List<KeepClient> keepClients = keepRegisterService.lambdaQuery().like(StringUtils.isNotBlank(searchDto.getKeyword()),KeepClient::getServiceName,searchDto.getKeyword()).list();
        return ResponseResult.success(new PageInfo<>(keepClients));
    }

    @ApiOperation("应用创建")
    @PostMapping("")
    public ResponseResult<KeepCreateVo> create(@RequestBody @Validated KeepClientCreateDto createDto){
        KeepClient keepClient = JSONObject.parseObject(JSON.toJSONString(createDto), KeepClient.class);
        keepRegisterService.save(keepClient);
        return ResponseResult.success(KeepCreateVo.builder().id(keepClient.getId()).build());
    }

    @ApiOperation("应用修改")
    @PutMapping("/{id}")
    public ResponseResult uppdate(@PathVariable Integer id,@RequestBody @Validated KeepClientCreateDto createDto){
        KeepClient keepClient = JSONArray.parseObject(JSON.toJSONString(createDto), KeepClient.class);
        keepClient.setId(id);
        keepRegisterService.updateById(keepClient);
        return ResponseResult.success();
    }

    @ApiOperation("应用删除")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Integer id){
        keepRegisterService.removeById(id);
        return ResponseResult.success();
    }

}

