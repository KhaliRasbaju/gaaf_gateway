package com.udi.gaaf.gateway.security;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTDecoderConfig {
	
	@Value("${api.jwt.secret}")
	private String secret;
	
	
	
	
	
}
