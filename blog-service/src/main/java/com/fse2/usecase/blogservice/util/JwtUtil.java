package com.fse2.usecase.blogservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private String SECRET_KEY = "mySuperSecretKeyForJWTcbhjbsdjhvhgsvcbcjhsgkjsdbvjhsbcjbScjhvjasbjhcsbjhvjhsbcjhsvjvhjvsjshvcvhcbjavcjsbvjhsvchg";
	
	public String getUserIdFromJwtToken(String token) {
		token = token.replace("Bearer ","").trim();
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getId();
	}

	public boolean validateJwtToken(String authToken) {
		authToken = authToken.replace("Bearer ","").trim();
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
