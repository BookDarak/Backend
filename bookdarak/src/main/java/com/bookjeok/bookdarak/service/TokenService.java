package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.dto.user.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
public class TokenService {
    @Value("${spring.jwt.timeOut}")
    private Long timeOut;

    @Value("${spring.jwt.secret}")
    String secret;

    public String getJWTokenString(String userId){
        log.info("##### getJWTokenString #####");

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setIssuer(userId) // token 발행 주체 id
                .setSubject("bookdarak") // token 발급 목적
                .claim("email", "sugar@naver.com") // 나중에 토큰 검증할 데이터들을 claim라는 이름으로 필요한 만큼 추가
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(timeOut))) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Token createToken(String userId){
        log.info("##### createToken #####");
        String jwTokenString = getJWTokenString(userId);

        Token token = Token.builder()
                .tokenId(userId)
                .jwTokenString(jwTokenString)
                .build();

        log.debug("##### Token Created #####" + userId);
        return token;
    }

    /**
     * expireToken
     * 토큰을 지운다. 또는 'black token'으로 등록한다.
     * Redis 같은 DB로 저장해서 black token으로 관리하는 것이 좋으나
     * RDB에 생성해서 관리해도 무방하다.
     * 참고 https://velog.io/@boo105/Redis-%EB%A5%BC-%ED%86%B5%ED%95%9C-JWT-Blacklist-%EA%B5%AC%ED%98%84
     */

    /**
     * validateToken
     * Black List에 Token이 존재하는지 확인한다.
     */

}
