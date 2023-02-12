package com.keep.auth.filter;

import cn.hutool.core.util.StrUtil;
import com.keep.auth.AuthUserContext;
import com.keep.auth.adapter.AuthConfigAdapter;
import com.keep.auth.fegin.client.UserCleint;
import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.common.core.handler.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权过滤，只要实现AuthConfigAdapter接口，添加对应路径即可：
 *
 * @author FrozenWatermelon
 * @date 2020/7/11
 */
@Component
public class AuthFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	@Autowired
	private AuthConfigAdapter authConfigAdapter;

	@Autowired
	private HttpHandler httpHandler;

	@Autowired
	private UserCleint userCleint;



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String accessToken = req.getHeader("Authorization");
		if (StrUtil.isBlank(accessToken)) {
			httpHandler.printServerResponseToWeb(ResponseResult.fail(ResponseResult.ReturnCode.ERROR));
			return;
		}

		UserInfoVo userInfoVo = userCleint.userInfo(accessToken);
		try {
			// 保存上下文
			AuthUserContext.setUserInfoVo(userInfoVo);

			chain.doFilter(request, response);
		}
		finally {
			AuthUserContext.remove();
		}
	}

//
//	@Autowired
//	private TokenFeignClient tokenFeignClient;
//
//	@Autowired
//	private PermissionFeignClient permissionFeignClient;
//
//	@Autowired
//	private FeignInsideAuthConfig feignInsideAuthConfig;
//
//	public void doFilte11r(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse resp = (HttpServletResponse) response;
//
//		if (!feignRequestCheck(req)) {
//			httpHandler.printServerResponseToWeb(ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
//			return;
//		}
//
//		if (Auth.CHECK_TOKEN_URI.equals(req.getRequestURI())) {
//			chain.doFilter(req, resp);
//			return;
//		}
//
//
//		List<String> excludePathPatterns = authConfigAdapter.excludePathPatterns();
//
//		// 如果匹配不需要授权的路径，就不需要校验是否需要授权
//		if (CollectionUtil.isNotEmpty(excludePathPatterns)) {
//			for (String excludePathPattern : excludePathPatterns) {
//				AntPathMatcher pathMatcher = new AntPathMatcher();
//				if (pathMatcher.match(excludePathPattern, req.getRequestURI())) {
//					chain.doFilter(req, resp);
//					return;
//				}
//			}
//		}
//
//		String accessToken = req.getHeader("Authorization");
//
//		if (StrUtil.isBlank(accessToken)) {
//			httpHandler.printServerResponseToWeb(ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
//			return;
//		}
//
//		// 校验token，并返回用户信息
//		ServerResponseEntity<UserInfoInTokenBO> userInfoInTokenVoServerResponseEntity = tokenFeignClient
//				.checkToken(accessToken);
//		if (!userInfoInTokenVoServerResponseEntity.isSuccess()) {
//			httpHandler.printServerResponseToWeb(ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
//			return;
//		}
//
//		UserInfoInTokenBO userInfoInToken = userInfoInTokenVoServerResponseEntity.getData();
//
//		// 需要用户角色权限，就去根据用户角色权限判断是否
//		if (!checkRbac(userInfoInToken,req.getRequestURI(), req.getMethod())) {
//			httpHandler.printServerResponseToWeb(ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
//			return;
//		}
//
//		try {
//			// 保存上下文
//			AuthUserContext.set(userInfoInToken);
//
//			chain.doFilter(req, resp);
//		}
//		finally {
//			AuthUserContext.clean();
//		}
//
//	}
//
//	private boolean feignRequestCheck(HttpServletRequest req) {
//		// 不是feign请求，不用校验
//		if (!req.getRequestURI().startsWith(FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX)) {
//			return true;
//		}
//		String feignInsideSecret = req.getHeader(feignInsideAuthConfig.getKey());
//
//		// 校验feign 请求携带的key 和 value是否正确
//		if (StrUtil.isBlank(feignInsideSecret) || !Objects.equals(feignInsideSecret,feignInsideAuthConfig.getSecret())) {
//			return false;
//		}
//		// ip白名单
//		List<String> ips = feignInsideAuthConfig.getIps();
//		// 移除无用的空ip
//		ips.removeIf(StrUtil::isBlank);
//		// 有ip白名单，且ip不在白名单内，校验失败
//		if (CollectionUtil.isNotEmpty(ips)
//				&& !ips.contains(IpHelper.getIpAddr())) {
//			logger.error("ip not in ip White list: {}, ip, {}", ips, IpHelper.getIpAddr());
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 用户角色权限校验
//	 * @param uri uri
//	 * @return 是否校验成功
//	 */
//	public boolean checkRbac(UserInfoInTokenBO userInfoInToken, String uri, String method) {
//
//		if (!Objects.equals(SysTypeEnum.PLATFORM.value(), userInfoInToken.getSysType()) && !Objects.equals(SysTypeEnum.MULTISHOP.value(), userInfoInToken.getSysType())) {
//			return true;
//		}
//
//		ServerResponseEntity<Boolean> booleanServerResponseEntity = permissionFeignClient
//				.checkPermission(userInfoInToken.getUserId(), userInfoInToken.getSysType(),uri,userInfoInToken.getIsAdmin(),HttpMethodEnum.valueOf(method.toUpperCase()).value() );
//
//		if (!booleanServerResponseEntity.isSuccess()) {
//			return false;
//		}
//
//		return booleanServerResponseEntity.getData();
//	}

}
