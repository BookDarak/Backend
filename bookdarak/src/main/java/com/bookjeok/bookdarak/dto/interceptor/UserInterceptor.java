package com.bookjeok.bookdarak.dto.interceptor;

import com.bookjeok.bookdarak.dto.token.Token;
import com.bookjeok.bookdarak.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Value("${spring.jwt.secret}")
    String secret;

    public boolean refreshWhenValid(Claims claims, String jsonWebToken, String requestMethod, String requestUri){
        try {
            // Token이 만료되면 Exception이 발생하나, 혹시 몰라서 if로 한번 더 처리함. 안해도 됨. debug 찍어보고 할 것
            // 날짜가 현재 시간보다 작으면 return false
            Date exp = claims.getExpiration();
            Date now = Date.from(Instant.now());

            if (exp.before(now)){
                return false;
            }

        } catch (MalformedJwtException e){
            return false;
        }

        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle ======================================================");
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String requestMethod = request.getMethod();
        String requestUri = request.getRequestURI();

        // 토큰은 헤더에만 보관
        String jsonWebToken = request.getHeader("jwtoken");
        log.debug("#### request of jsonWebToken ####" + request.getRequestURI() + " " + jsonWebToken);

        if (!StringUtils.hasText(jsonWebToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Decoders.BASE64.decode(secret))
                    .build().parseClaimsJws(jsonWebToken).getBody();

            // jwt로 체크해야 할 로직들을 검증한 후에 true면 다시 새로운 토큰 추가
            if (refreshWhenValid(claims, jsonWebToken, requestMethod, requestUri)) {
                Token token = tokenService.createToken(claims.getIssuer());
                log.debug("##### 다시 생성된 tokenData #####: " + token.toString());

                response.setHeader("jwtoken", token.getJwTokenString());
                return true;
            }
        } catch (NullPointerException npe) {
            log.error(npe.getMessage(), npe);
        } catch (ExpiredJwtException jwtException) {
            log.error(jwtException.getMessage(), jwtException);
        }

        log.debug("No token. jwt:" + jsonWebToken + ")");

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
