package com.rvk.mtbs.security;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rvk.mtbs.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final JwtProperties jwtProperties;

	public String generateToken(UserDetails userDetails) {
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		
		return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date()).claim("role", roles)
				.expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
				.signWith(getSecretKey()).compact();
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		return extractClaim(token, Claims::getSubject).equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private <T> T extractClaim(String token, Function<Claims, T> resolver) {
		return resolver.apply(extractAllClaims(token));
	}

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
