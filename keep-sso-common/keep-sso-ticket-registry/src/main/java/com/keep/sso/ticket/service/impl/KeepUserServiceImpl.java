package com.keep.sso.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.common.core.domain.constants.CommanConstants;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.common.core.expection.CustomExpection;

import com.keep.sso.ticket.convert.ObjConvertMapper;
import com.keep.sso.ticket.entity.KeepUser;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.entity.param.LoginParam;
import com.keep.sso.ticket.entity.param.SysUserParam;
import com.keep.sso.ticket.mapper.KeepUserMapper;
import com.keep.sso.ticket.service.KeepUserService;
import com.keep.sso.ticket.utils.JwtTokenUtils;
import com.keep.sso.ticket.entity.vo.OauthLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
@Slf4j
@Service
public class KeepUserServiceImpl extends ServiceImpl<KeepUserMapper, KeepUser> implements KeepUserService {

    @Autowired
    private TokenMultRegistryServiceImpl tokenMultRegistryService;

    @Transactional
    @Override
    public OauthLoginVo login(LoginParam loginParam) {
        List<KeepUser> list = this.lambdaQuery().select(KeepUser::getId, KeepUser::getUsername, KeepUser::getPassword).eq(KeepUser::getUsername, loginParam.getUserName()).list();
        if(CollectionUtils.isEmpty(list)){
            throw new CustomExpection("用户名或者密码错误");
        }
        KeepUser user = list.get(0);
        if(!Objects.equals(user.getPassword(),loginParam.getPassWord())){
            throw new CustomExpection("用户名或者密码错误");
        }
        // 生成 tgt refeshToken，accessToken
        return tokenMultRegistryService.generatorToken(user,loginParam.getClientId(),loginParam.getDeviceType());
    }

    @Override
    public UserInfoVo getUserInfo(String token) {
        Ticket ticket = tokenMultRegistryService.getTicketById(token);
        long start = System.currentTimeMillis();
        QueryWrapper<KeepUser> query = new QueryWrapper<>();
        query.lambda().eq(KeepUser::getUsername,ticket.getUsername());
        KeepUser keepUser = this.getOne(query);
        log.info("查询用户信息耗时：{} ms",System.currentTimeMillis() - start);
        if(Objects.isNull(keepUser)){
            throw new CustomExpection("用户不存在");
        }
        return ObjConvertMapper.INSTANCE.toUserInfoVo(keepUser);
    }

    @Override
    public void create(SysUserParam param) {
        KeepUser keepUser = ObjConvertMapper.INSTANCE.toSrs(param);
        this.save(keepUser);
    }

}
