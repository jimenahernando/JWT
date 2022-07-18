package com.jimenuzca.restjwt.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jimenuzca.restjwt.security.JwtAuthenticationEntryOption;
import com.jimenuzca.restjwt.security.JwtAuthenticationProvider;
import com.jimenuzca.restjwt.security.JwtAuthenticationTokenFilter;
import com.jimenuzca.restjwt.security.JwtSuccessHandler;

@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private JwtAuthenticationProvider authenticationProvider;
	
	@Autowired 
	private JwtAuthenticationEntryOption entryPoint;
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(authenticationProvider));
	}
	
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilter() {
		JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
		
		return filter;
	}
	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests().antMatchers("**/api/**").authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(entryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //indica que no vamosa  guardar sesison que cada llamada es independientee
	
		http.addFilterAfter(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
	}


}
