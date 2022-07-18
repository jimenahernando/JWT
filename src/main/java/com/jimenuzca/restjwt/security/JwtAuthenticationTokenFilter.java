package com.jimenuzca.restjwt.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.jimenuzca.restjwt.constants.Constants;
import com.jimenuzca.restjwt.model.JwtAuthenticationToken;


public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter{

	public JwtAuthenticationTokenFilter() {
		super("/api/");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		// obtenemos el header
		String header = request.getHeader(Constants.AUTHORIZATION_HEADER);
		
		//preguntamos si el header esta vacio o si comienza con Bearer 
		if (header == null || !header.startsWith(Constants.TOKEN)) {
			throw new RuntimeException("JWT ES INCORRECTO O NO HA LLEGADO NADA");
		}
		
		//obtenemos el token
		String authenticationToken = header.substring(7);
		
		//clase que creamso para el token y que extiende de UsernamePasswordAuthenticationToken
		JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
		
		//verifica si es un token valido y si es valido le damos acceso al recurso solicitado y si no no
		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
	
	

}
