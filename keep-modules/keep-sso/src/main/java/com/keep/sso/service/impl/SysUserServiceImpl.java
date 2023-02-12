package com.keep.sso.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.common.core.domain.constants.CommanConstants;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.common.core.expection.CustomExpection;
import com.keep.sso.convert.ObjConvertMapper;
import com.keep.sso.entity.SysUser;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.param.SysUserParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.mapper.SysUserMapper;
import com.keep.sso.service.SysUserService;
import com.keep.sso.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        SysUser sysUser = this.getById(Long.valueOf(obj.toString()));
        log.info("查询用户信息耗时：{} ms",System.currentTimeMillis() - start);
        if(Objects.isNull(sysUser)){
            throw new CustomExpection("用户不存在");
        }
        return ObjConvertMapper.INSTANCE.toUserInfoVo(sysUser);
    }

    @Override
    public void create(SysUserParam param) {
        SysUser sysUser = ObjConvertMapper.INSTANCE.toSrs(param);
        this.save(sysUser);
    }

}
