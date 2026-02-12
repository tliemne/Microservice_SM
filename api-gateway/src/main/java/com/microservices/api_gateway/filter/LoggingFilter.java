package com.microservices.api_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        long start = System.currentTimeMillis();

        log.info("[IN ] {} {}", request.getMethod(), request.getURI().getPath());

        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    ServerHttpResponse response = exchange.getResponse();
                    long time = System.currentTimeMillis() - start;

                    log.info("[OUT] {} {} -> {} ({} ms)",
                            request.getMethod(),
                            request.getURI().getPath(),
                            response.getStatusCode(),
                            time);
                })
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}