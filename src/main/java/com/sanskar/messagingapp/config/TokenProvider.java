package com.sanskar.messagingapp.config;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenProvider {
	
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication authentication) {
		
		
		String jwt =  Jwts.builder().setIssuer("Sanskar")
				.setIssuedAt(new Date(new Date().getTime()+86400000))
				.claim("email", authentication.getName())
				.signWith(key)
				.compact();
				
		return jwt;
	}
	
	public String getEmailFromToken(String token) {
		
		token = token.substring(7);
		 Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		 String email = String.valueOf(claims.get("email"));
		 return email;
	}

}
