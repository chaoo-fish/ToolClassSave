/*
package com.chaoo.gateway.predicates;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

*/
/**
 * 自定义断言
 *//*

@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {

    public AgeRoutePredicateFactory() {
        super(AgeRoutePredicateFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("minAge", "maxAge");
    }

    // 断言逻辑
    public Predicate<ServerWebExchange> apply(final AgeRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>(){
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                // 从 serverWebExchange 获取到传入的参数
                String ageStr = serverWebExchange.getRequest().getQueryParams().getFirst("age");
                // 如果有参数
                if (StringUtils.hasLength(ageStr)) {
                    int age = Integer.parseInt(ageStr);
                    return age > config.getMinAge() && age < config.getMaxAge();
                }
                // 如果没有参数直接返回 false
                return false;
            }
        };
    }

    @Data
    public static class Config {
        private Integer minAge;
        private Integer maxAge;
    }
}
*/
