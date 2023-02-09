package com.keep.common.interceptor;

import com.keep.common.domain.entity.UserVoHolder;
import com.keep.common.fegin.vo.UserInfoVo;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created on 2022/4/29 15:02.
 *
 * @author
 * Description:
 */
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        /*UserInfoVo userInfo = (UserInfoVo) UserVoHolder.getUserTo();
        if (Objects.nonNull(userInfo)) {
            // 父子线程内存信息拷贝
            requestTemplate.header(JwtUtil.USERID, userInfo.getUserId());
            requestTemplate.header(JwtUtil.AUTHORIZATION_NAME, userInfo.getToken());
        } else {
            String userId = SrpUtils.getCurrentUserId();
            String token = SrpUtils.getToken();
            if (Objects.isNull(userId)) {
                return;
            }
            if (StrUtil.isBlank(userId)) {
                Map map = requestTemplate.queries();
                if (map.containsKey(JwtUtil.USERID)) {
                    userId = (String) ((List) map.get(JwtUtil.USERID)).get(0);
                }
            }
            requestTemplate.header(JwtUtil.USERID, userId);
        }*/

    }
}
