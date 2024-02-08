package com.ilooksyou.util;

import com.ilooksyou.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static String key = "zjdbfagvqruuyzbhgdfpeoajnebxiueban";

    public static String createToken(UserDto userDto){
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());
        Map<String,Object> map = new HashMap<>();
        map.put("email",userDto.email());
        map.put("username",userDto.username());
        return Jwts.builder().setIssuer("LooksYou").setIssuedAt(new Date())
                .addClaims(map)
                .setExpiration(new Date(new Date().getTime()+3600000))
                .signWith(secretKey).compact();
    }

    public static UserDto parseToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return new UserDto(String.valueOf(claims.get("email")),String.valueOf(claims.get("username")));
    }
}
