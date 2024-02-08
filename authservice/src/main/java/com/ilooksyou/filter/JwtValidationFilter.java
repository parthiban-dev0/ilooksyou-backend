package com.ilooksyou.filter;

import com.ilooksyou.dto.UserDto;
import com.ilooksyou.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token != null){
            UserDto userDto = JwtUtil.parseToken(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.email(),null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            System.out.println("Token is missing");
        }
        filterChain.doFilter(request,response);
    }
}
