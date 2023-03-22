package com.keep.sso.controller;


import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.sso.entity.dto.SearchDto;
import com.keep.sso.entity.vo.KeepMenuOperationVo;
import com.keep.sso.service.KeepMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
 * @since 2023-03-20
 */
@Api(tags = "权限树列表")
@RestController
@RequestMapping("/keepMenuOperations")
@RequiredArgsConstructor
public class KeepMenuOperationController {

    private final KeepMenuService keepMenuService;


    @ApiOperation("权限树列表")
    @GetMapping("")
    public ResponseResult<List<KeepMenuOperationVo>> list(){
        return ResponseResult.success(keepMenuService.listAll());
    }


}

