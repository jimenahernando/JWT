package com.jimenuzca.restjwt.security;

import org.springframework.stereotype.Component;

import com.jimenuzca.restjwt.constants.Constants;
import com.jimenuzca.restjwt.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

	// recibe ese token en formato json
	public JwtUser validate(String token) {
		JwtUser jwtUser = null;
		
		//para extraer el payload usamos claims
		try {
			Claims body = Jwts.parser()
					.setSigningKey(Constants.YOUR_SECRET)
					.parseClaimsJws(token)
					.getBody();
			jwtUser =  new JwtUser();
			jwtUser.setUsername(body.getSubject());
			jwtUser.setId(Long.parseLong((String) body.get(Constants.USER_ID)));
			jwtUser.setRole((String) body.get(Constants.ROLE));
		} catch (Exception e) {
			System.out.println("");
		}
		
		return jwtUser;
	}
}
