/*
package com.chaoo.gateway.predicates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

*/
/**
 * 自定义过滤器
 *//*

@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    public static final String PARTS_KEY = "parts";

    public LogGatewayFilterFactory() {
        super(LogGatewayFilterFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("flag");
    }
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if (config.isFlag()){
                    System.out.println("log 过滤器已经生效了");
                }
                // 执行下一个过滤器
                return chain.filter(exchange);
            }
        };
    }

    @Data
    @NoArgsConstructor
    public static class Config {
        private boolean flag;
    }
}
*/
