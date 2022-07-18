package com.jimenuzca.restjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimenuzca.restjwt.model.JwtUser;
import com.jimenuzca.restjwt.model.Login;
import com.jimenuzca.restjwt.security.JwtGenerator;

//controlador que nos va a devolver el token
@RestController
@RequestMapping("/token")
public class TokenController {

	private JwtGenerator jwtGenerator;
	
	public TokenController(JwtGenerator jwtGenerator) {
		this.jwtGenerator = jwtGenerator;
	}
	
	@PostMapping
	public ResponseEntity<String> generate(@RequestBody final Login login){
		
		JwtUser jwtUser = new JwtUser();
		
		jwtUser = existUser(login);
		if(jwtUser != null) {
			return new ResponseEntity<String>(jwtGenerator.generate(jwtUser), HttpStatus.OK);
		}
		
		return  new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}
	
	
	private JwtUser existUser(Login login) {
		// esto es loq eu chequeariamos en la base de datos
		if(login.getUser().equals("JIMENA") && login.getPassword().equals("1234")) {
			JwtUser jwtUser = new JwtUser();
			jwtUser.setUsername(login.getUser());
			jwtUser.setId(new Long(2));
			jwtUser.setRole("admin");
			
			return jwtUser;
		}
		
		return null;
	}
}
