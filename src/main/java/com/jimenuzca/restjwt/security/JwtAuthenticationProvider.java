package com.jimenuzca.restjwt.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jimenuzca.restjwt.model.JwtAuthenticationToken;
import com.jimenuzca.restjwt.model.JwtUser;
import com.jimenuzca.restjwt.model.JwtUserDetails;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	//inyectamos el validador
	@Autowired
	private JwtValidator validator;
	

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		
		String token = jwtAuthenticationToken.getToken();
		
		JwtUser jwtUser = validator.validate(token);
		
		if(jwtUser == null) {
			throw new RuntimeException("JWT es incorrecto");
		}
		
		List<GrantedAuthority> grandAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
		
		return new JwtUserDetails(jwtUser.getUsername(), token, jwtUser.getId(), grandAuthorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
