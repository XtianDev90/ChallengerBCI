package cl.prueba.globalLogic.configuration;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.prueba.globalLogic.service.TokenService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	protected final TokenService tokenService;

	@Override
	  public void configure(HttpSecurity http) {
	    JwtTokenFilter customFilter = new JwtTokenFilter(tokenService);
	    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	  }
}
