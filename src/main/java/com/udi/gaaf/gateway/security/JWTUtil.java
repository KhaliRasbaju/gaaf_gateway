package com.udi.gaaf.gateway.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTUtil {
	
	@Value("${api.jwt.secret}")
	private String secret;
	
	
	public DecodedJWT validarToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.require(algorithm).build().verify(token);
	}
	
	
}
