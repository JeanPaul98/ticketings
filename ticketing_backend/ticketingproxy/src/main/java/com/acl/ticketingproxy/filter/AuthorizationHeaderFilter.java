package com.acl.ticketingproxy.filter;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private Environment env;

public AuthorizationHeaderFilter() {
    super(Config.class);
}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Auhtorisation header", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");
            if(!isJwtValid(jwt)) {
                return onError(exchange, "Is not valid", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();

    }

    private boolean isJwtValid(String jwt) {
        boolean isValid = true;
        String tokenSecret = env.getProperty("app.jwtSecret");
        String subject = null;
        byte[] secretKeyByte = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey signinkey = new SecretKeySpec(secretKeyByte, SignatureAlgorithm.HS256.getJcaName());
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(signinkey)
                .build();
        try{
            Jwt<Header,Claims> parseToken = jwtParser.parse(jwt);
            subject = parseToken.getBody().getSubject();

        }catch (Exception ex) {
            isValid = false;
        }
        if( subject==null || subject.isEmpty()) {
            isValid  = false;
        }

        return true;
    }

    public static class Config {
        // TODO: 29/08/2023 Put somme config
    }


}
