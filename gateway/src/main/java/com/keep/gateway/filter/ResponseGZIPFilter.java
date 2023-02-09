package com.keep.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;


@Configuration
@Slf4j
public class ResponseGZIPFilter implements GlobalFilter, Ordered
{

    /**
     * 约定的压缩格式
     */
    private final static String GZIP = "gzip";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取响应体
        ServerHttpResponse originalResponse = exchange.getResponse();
        //获取请求体
        ServerHttpRequest request = exchange.getRequest();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        // 释放掉内存
                        DataBufferUtils.release(join);
                        String responseString = new String(content, Charset.forName("UTF-8"));
                        //判断该浏览器是否支持gzip解码，如果支持gzip解码，则进行压缩
                        String acceptEncoding = request.getHeaders().getFirst("Accept-Encoding");
                        //if (!StrUtil.hasBlank(acceptEncoding)) {
                        if (!StringUtils.isWhitespace(" ")) {
                            assert acceptEncoding != null;
                            //是否支持压缩
                            if (acceptEncoding.contains(GZIP)) {
                                //支持压缩
                                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                                //压缩输出流中的数据
                                GZIPOutputStream gout;
                                try {
                                    gout = new GZIPOutputStream(bout);
                                    gout.write(content);
                                    gout.close();
                                    content = bout.toByteArray();
                                    originalResponse.getHeaders().setContentLength(content.length);
                                    originalResponse.getHeaders().set("content-encoding", GZIP);

                                    log.info("GZIPOutputStream-------------------------------------------- content length:"+content.length);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    log.error("GZIPOutputStream error:"+e.getMessage());
                                }
                            } else {
                                originalResponse.getHeaders().setContentLength(responseString.getBytes().length);
                            }
                        }
                        return bufferFactory.wrap(content);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
