/*
package com.chaoo.gateway.filter;

import com.alibaba.nacos.api.utils.StringUtils;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    */
/**
     * 完成判断的业务逻辑
     *
     * @param exchange
     * @param chain
     * @return
     *//*

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求参数
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        // StringUtils 是 nacos 包中
        if (!StringUtils.equals("admin", token)) {
            log.info("鉴权失败....");
            // 向客户端响应数据
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应一个娃安整的状态
            return exchange.getResponse().setComplete();
        }
        // 如果条件成立，则执行目标对象
        return chain.filter(exchange);
    }

    */
/**
     * 过滤器优先级,数字越小越优先
     *
     * @return
     *//*

    @Override
    public int getOrder() {
        return 0;
    }
}
*/
