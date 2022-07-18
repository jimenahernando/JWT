package com.jimenuzca.restjwt.security;

import org.springframework.stereotype.Component;

import com.jimenuzca.restjwt.constants.Constants;
import com.jimenuzca.restjwt.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//clase que genera el token
@Component
public class JwtGenerator {
	
	public String generate(JwtUser jwtUser) {
		
		//creamos las claims
		
		Claims claims = Jwts.claims()
				.setSubject(jwtUser.getUsername());
		claims.put(Constants.USER_ID, String.valueOf(jwtUser.getId()));

		claims.put(Constants.ROLE, jwtUser.getRole());
		
		//construye el token
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, Constants.YOUR_SECRET)
				.compact();
		
	}

}
