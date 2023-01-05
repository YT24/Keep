package com.keep.sso.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.keep.common.constants.CommanConstants;
import com.keep.common.expection.CustomExpection;
import com.keep.common.utils.JwtTokenUtils;
import com.keep.sso.entity.SysUser;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.entity.vo.UserInfoVo;
import com.keep.sso.mapper.SysUserMapper;
import com.keep.sso.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public LoginVo login(LoginParam loginParam) {
        List<SysUser> list = this.lambdaQuery().select(SysUser::getId,SysUser::getUsername,SysUser::getPassword).eq(SysUser::getUsername, loginParam.getUserName()).list();
        if(CollectionUtils.isEmpty(list)){
            throw new CustomExpection("用户名或者密码错误");
        }
        SysUser user = list.get(0);
        if(!Objects.equals(user.getPassword(),loginParam.getPassWord())){
            throw new CustomExpection("用户名或者密码错误");
        }

        String key = "sys:user:" + user.getId();
        Object token = JwtTokenUtils.generatorToken(new HashMap<>(),user.getId().toString(), CommanConstants.AT_EXPIRED_TIME);
        redisTemplate.opsForValue().set(key,token.toString(),CommanConstants.AT_EXPIRED_TIME,TimeUnit.SECONDS);
       /* if(Objects.nonNull(token)){
            redisTemplate.expire(key,CommanConstants.AT_EXPIRED_TIME, TimeUnit.SECONDS);
        }else{
            token = JwtTokenUtils.generatorToken(new HashMap<>(),user.getId().toString(), CommanConstants.AT_EXPIRED_TIME);
            redisTemplate.opsForValue().set(key,token.toString(),CommanConstants.AT_EXPIRED_TIME,TimeUnit.SECONDS);
        }*/
        LoginVo vo = new LoginVo();
        vo.setAccessToken(token.toString());
        vo.setExpiresIn(CommanConstants.AT_EXPIRED_TIME);
        return vo;
    }

    @Override
    public UserInfoVo getUserInfo(String token) {
        Map<String, Object> parserToken = JwtTokenUtils.parserToken(token);
        Object obj = parserToken.get(CommanConstants.USER_ID);
        long start = System.currentTimeMillis();
        List<SysUser> users = this.lambdaQuery().eq(SysUser::getId, obj.toString()).list();
        log.info("查询用户信息耗时：{} ms",System.currentTimeMillis() - start);
        if(CollectionUtils.isEmpty(users)){
            throw new CustomExpection("用户不存在");
        }
        UserInfoVo vo = new UserInfoVo();
        vo.setId(users.get(0).getId());
        vo.setMobile(users.get(0).getMobile());
        vo.setDeptId(users.get(0).getDeptId());
        vo.setUserName(users.get(0).getUsername());
        return vo;
    }
}
