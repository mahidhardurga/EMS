package com.ems.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter{
	
	private static Logger logger = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/login")) {
			filterChain.doFilter(request, response);
		}else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
				String token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(token);
				String userName = decodedJWT.getSubject();
				String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				Stream.of(roles).forEach(role -> {
					authorities.add(new SimpleGrantedAuthority(role));
				});
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(userName, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
				}catch (Exception e) {
					logger.error("Error logging in: {}",e.getMessage());
					response.setHeader("error", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
					//response.sendError(HttpStatus.FORBIDDEN.value());
					Map<String,String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			}else {
				filterChain.doFilter(request, response);
			}
		}
		
	}
	
	

}
