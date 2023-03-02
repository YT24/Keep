package com.keep.common.elasticsearch.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * restHighLevelClient 客户端配置类
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchConfig {
 
    // es host ip 地址（集群）
    private String hosts;
    // 端口 9200
    private int port;
    // es用户名
    private String username;
    // es密码
    private String password;
    // es集群名称
    private String clusterName;

    public static final String SPLIT = ",";
    public static int CONNECT_TIMEOUT_MILLIS = 10000;
    public static int SOCKET_TIMEOUT_MILLIS = 30000;
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 5000;
    public static int MAX_CONN_PER_ROUTE = 10;
    public static int MAX_CONN_TOTAL = 30;
 
    @Bean
    public RestHighLevelClient restHighLevelClient() {

        // 构建连接对象
        RestClientBuilder builder = getHttpRequestBuild(hosts,port);
 
        // 设置用户名、密码
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(username,password));

        // 连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
        // 连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });
 
        return new RestHighLevelClient(builder);
    }
    private RestClientBuilder getHttpRequestBuild(String hosts, int port) {
        String[] split = hosts.split(SPLIT);
        List<String> strings = Arrays.asList(split);
        List<HttpHost> list = new ArrayList<>();
        for (String string : strings) {
            HttpHost httpHost = new HttpHost(string, port);
            list.add(httpHost);
        }
        // 设置用户名密码认证
        /*if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
            String auth = Base64.encodeBase64String((userName+":"+password).getBytes());
            builder.setDefaultHeaders(new BasicHeader[]{new BasicHeader(CommanConstants.HEADER, CommanConstants.BASIC+" " + auth)});
        }*/
        return RestClient.builder(list.toArray(new HttpHost[list.size()]));
    }
}