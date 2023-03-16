package com.keep.sso.controller;


import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.sso.convert.KeepClientsConvert;
import com.keep.sso.entity.KeepClient;
import com.keep.sso.entity.vo.KeepClientVo;
import com.keep.sso.service.KeepClientsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public ResponseResult<KeepClientVo> list(){
        List<KeepClient> keepClients = keepRegisterService.list();
        return ResponseResult.success(KeepClientsConvert.INSTANCE.toKeepClientsVos(keepClients));
    }

}

