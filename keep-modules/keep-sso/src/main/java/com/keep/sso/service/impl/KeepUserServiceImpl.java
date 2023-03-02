package com.keep.sso.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.common.core.domain.constants.CommanConstants;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.common.core.expection.CustomExpection;
import com.keep.redis.service.TokenRegistryService;
import com.keep.sso.convert.ObjConvertMapper;
import com.keep.sso.entity.KeepUser;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.param.SysUserParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.mapper.KeepUserMapper;
import com.keep.sso.service.KeepUserService;
import com.keep.sso.utils.JwtTokenUtils;
import com.keep.sso.utils.TicketGenUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Qualifier("tokenJvmRegistryService")
    private TokenRegistryService tokenJvmRegistryService;

    @Override
    public LoginVo login(LoginParam loginParam) {
        List<KeepUser> list = this.lambdaQuery().select(KeepUser::getId, KeepUser::getUsername, KeepUser::getPassword).eq(KeepUser::getUsername, loginParam.getUserName()).list();
        if(CollectionUtils.isEmpty(list)){
            throw new CustomExpection("用户名或者密码错误");
        }
        KeepUser user = list.get(0);
        if(!Objects.equals(user.getPassword(),loginParam.getPassWord())){
            throw new CustomExpection("用户名或者密码错误");
        }

        String key = "sys:user:" + user.getId();
        Object token = JwtTokenUtils.generatorToken(new HashMap<>(),user.getId().toString(), CommanConstants.AT_EXPIRED_TIME);
        tokenJvmRegistryService.addToken(TicketGenUtils.getTokenCacheTicket(user.getId()));
        LoginVo vo = new LoginVo();
        vo.setAccessToken(token.toString());
        vo.setExpiresIn(CommanConstants.AT_EXPIRED_TIME);
        log.info("登录成功");
        return vo;
    }

    @Override
    public UserInfoVo getUserInfo(String token) {
        Map<String, Object> parserToken = JwtTokenUtils.parserToken(token);
        Object obj = parserToken.get(CommanConstants.USER_ID);
        long start = System.currentTimeMillis();
        KeepUser keepUser = this.getById(Long.valueOf(obj.toString()));
        log.info("查询用户信息耗时：{} ms",System.currentTimeMillis() - start);
        if(Objects.isNull(keepUser)){
            throw new CustomExpection("用户不存在");
        }
        return ObjConvertMapper.INSTANCE.toUserInfoVo(keepUser);
    }

    @GlobalTransactional
    @Override
    public void create(SysUserParam param) {
        KeepUser keepUser = ObjConvertMapper.INSTANCE.toSrs(param);
        this.save(keepUser);
    }

}
