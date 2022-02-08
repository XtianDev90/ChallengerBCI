package cl.prueba.globalLogic.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import cl.prueba.globalLogic.service.TokenService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean{

	private final TokenService tokenService;
	
	@Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
		
        String token = tokenService.resolveToken((HttpServletRequest) req);
        if (token != null && !token.isEmpty() && tokenService.validateToken(token)) {
            Authentication auth = tokenService.getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(req, res);
    }

}
