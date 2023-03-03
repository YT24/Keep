package com.keep.sso.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.common.core.domain.constants.CommanConstants;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.common.core.expection.CustomExpection;
import com.keep.sso.convert.ObjConvertMapper;
import com.keep.sso.entity.KeepUser;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.param.SysUserParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.mapper.KeepUserMapper;
import com.keep.sso.service.KeepUserService;
import com.keep.sso.utils.JwtTokenUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        // 生成 tgt refeshToken，accessToken

        LoginVo vo = new LoginVo();
        vo.setAccessToken(null);
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
