package com.keep.gateway.config;//package com.example.gateway.config;
//
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.alibaba.nacos.common.utils.StringUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DefaultDataBufferFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import tech.valuesimplex.srpcommon.constant.GlobalConst;
//import tech.valuesimplex.srpcommon.dto.ResponseResult;
//import tech.valuesimplex.srpcommon.jwt.JwtUtil;
//import tech.valuesimplex.srpcommon.jwt.TokenInfo;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class AuthorizeFilter implements GlobalFilter, Ordered {
//    private final WebClient.Builder webClientBuilder;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 获取Request、Response对象
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        String uriPath = request.getURI().getPath();
//
//        //白名单接口
//        if (uriPath.contains("auth/login")
//                || uriPath.contains("auth/code")
//                || uriPath.contains("auth/refresh")
//                || uriPath.contains("api-docs")
//                || uriPath.contains("swagger-resources")
//                || uriPath.contains("webjars")
//                || uriPath.contains("swagger-ui")
//        ){
//            return chain.filter(exchange);
//        }
//
//        // 获取用户令牌信息
//        String token = request.getHeaders().getFirst(GlobalConst.AUTHORIZATION_NAME);
//        // 没有令牌，则拦截
//        if (StringUtils.isEmpty(token)) {
//            // 设置401状态码，提示用户没有权限，用户收到该提示后需要重定向到登陆页面
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.writeWith(Flux.just(response.bufferFactory()
//                    .wrap("用户登录已失效".getBytes(StandardCharsets.UTF_8))
//            ));
//        }
//
//        return webClientBuilder.baseUrl("http://srp-auth").build().get().uri(uriBuilder -> uriBuilder.path("/auth/verify").queryParam("token", token).build()).retrieve()
//                .onStatus(httpStatus -> !HttpStatus.OK.equals(httpStatus),
//                        clientResponse -> clientResponse.bodyToMono(ResponseResult.class).flatMap(responseDto -> {
//                            String msg = responseDto.getMsg();
//                            log.error("verifyToken error:"+msg);
//                            return Mono.error(new RuntimeException(msg));
//                        }))
//                .bodyToMono(String.class).flatMap(re -> {
//                    JSONObject json = JSONUtil.parseObj(re);
//                    if(json.get("code",Integer.class)==ResponseResult.ReturnCode.ERROR.getReturnCode().intValue()){
//                        return Mono.error(new RuntimeException("Token验证失败"));
//                    }
//
//                    TokenInfo tokenInfo = JwtUtil.getTokenInfo(token);
//
//                    String encodeName = null;
//                    try {
//                        encodeName = URLEncoder.encode(tokenInfo.getName(), "UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        //e.printStackTrace();
//                    }
//
//                    ServerHttpRequest mutableReq = request.mutate()
//                            .header(JwtUtil.USERID, String.valueOf(tokenInfo.getUserId()))
//                            .header(JwtUtil.USERNAME, tokenInfo.getUsername())
//                            .header(JwtUtil.NAME, encodeName)//TODO fix 中文乱码， 需要urlencode一下。 AUTHORIZATION_NAME encode = URLEncoder.encode("我是中文请求头", "UTF-8");  String decode = URLDecoder.decode(upperLimit, StandardCharsets.UTF_8.toString());
//                            .header(GlobalConst.AUTHORIZATION_NAME, token)
//                            .build();
//                    ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
//                    return chain.filter(mutableExchange);
//                }).onErrorResume(error -> {
//                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                    return response.writeWith(Mono.fromCallable(() -> new DefaultDataBufferFactory().wrap("用户登录已失效".getBytes(StandardCharsets.UTF_8))));
//                });
//    }
//
//    @Override
//    public int getOrder() {
//        return -100;
//    }
//}
