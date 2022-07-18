package com.jimenuzca.restjwt.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String token;
	private Long id;
	private Collection<? extends GrantedAuthority> autorities;

	public JwtUserDetails(String username, String token, Long id, List<GrantedAuthority> autorities) {
		super();
		this.username = username;
		this.token = token;
		this.id = id;
		this.autorities = autorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.autorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
