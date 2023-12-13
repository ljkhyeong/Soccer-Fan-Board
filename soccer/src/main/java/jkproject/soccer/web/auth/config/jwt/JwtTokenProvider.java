package jkproject.soccer.web.auth.config.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.web.auth.service.TokenAuthenticationService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final TokenAuthenticationService tokenAuthenticationService;

	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;

	@PostConstruct
	private void init() {
		key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	private Key getKey() {
		return key;
	}

	public String generateToken(String subject, TokenType tokenType) {
		Claims claims = Jwts.claims()
			.setId(tokenType.getName())
			.setSubject(subject);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + tokenType.getValidTimeMilli()))
			.signWith(getKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	private Optional<Cookie> findCookieByName(String name, HttpServletRequest request) {
		Cookie[] cookies = Optional.ofNullable(request.getCookies())
			.orElse(new Cookie[0]);
		return Arrays.stream(cookies)
			.filter(cookie -> cookie.getName().equals(name))
			.findFirst();
	}

	public String resolveToken(HttpServletRequest request, TokenType tokenType) {
		return findCookieByName(tokenType.getName(), request)
			.map(Cookie::getValue)
			.orElse(null);
	}

	private String getId(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getId();
	}

	private boolean getExpiration(String token, Date now) {
		return Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getExpiration()
			.before(now);
	}

	public String getSubject(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public boolean isTokenValid(String token) {
		try {
			boolean isValid = !getExpiration(token, new Date());
			boolean isAccessToken = getId(token).equals(TokenType.ACCESS.getName());
			return isValid && isAccessToken;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		UserAuthenticationDto userDto = tokenAuthenticationService.getUserAuthenticationDto(
			getSubject(token));
		return new UsernamePasswordAuthenticationToken(userDto, null, userDto.getAuthorities());
	}
}

