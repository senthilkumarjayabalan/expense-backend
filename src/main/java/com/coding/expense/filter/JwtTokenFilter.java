package com.coding.expense.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.coding.expense.controller.UserController;
import com.coding.expense.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
	public UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("JWT Token filter called ... ");
		
		String header = request.getHeader("Authorization");
		if( !StringUtils.hasText(header) || !header.startsWith("Bearer ") 
				|| !(( header.split(" ").length) == 2) || !StringUtils.hasText((header.split(" ")[1]))) {
			filterChain.doFilter(request,response);
			return;
		}
		
		
		final String token = header.split(" ")[1].trim();
		
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(UserController.SECRET_KEY)).parseClaimsJws(token).getBody();
		
		log.info("Subject " + claims.getSubject());
		log.info("Cliam " + claims.get("role"));
		
		UserDetails userDetails = userService.loadUserByUsername(claims.getSubject());
		
		UsernamePasswordAuthenticationToken
        authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, 
            userDetails.getAuthorities()
        );
		
		authentication
		.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request,response);
		
		log.info("JwtTokenFilter Ended ... ");
	}

}
