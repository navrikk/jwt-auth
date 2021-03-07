package com.nikshepav.jwtauth.config;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfiguration {
    @Bean("restTemplate")
    RestTemplate createRestTemplate(@Qualifier("genericHttpClient") HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters()
            .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Bean("httpPoolingManager")
    HttpClientConnectionManager poolingManager(HttpConnectionConfiguration connectionConfig) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(connectionConfig.getMaxTotalConnections());
        cm.setDefaultMaxPerRoute(connectionConfig.getMaxConnectionsPerRoute());
        cm.setValidateAfterInactivity(connectionConfig.getIdleConnectionsTimeout());
        return cm;
    }

    @Bean("genericHttpClient")
    HttpClient httpClient(
        @Qualifier("httpPoolingManager") HttpClientConnectionManager cm,
        HttpConnectionConfiguration httpConnectionConfiguration) {
        return HttpClients.custom()
            .setConnectionManager(cm)
            .evictIdleConnections(httpConnectionConfiguration.getIdleConnectionsTimeout(),
                TimeUnit.MILLISECONDS)
            .setDefaultRequestConfig(RequestConfig.custom()
                .setSocketTimeout(httpConnectionConfiguration.getSocketTimeout())
                .setConnectTimeout(httpConnectionConfiguration.getConnectionTimeout())
                .build())
            .build();
    }
}
