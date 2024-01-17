package jkproject.soccer.auth.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jkproject.soccer.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	//TODO 지금은 쿠키에서 토큰을 꺼내오는 방식이지만 확장 시 Authorization 헤더를 이용하도록 수정 필요
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = jwtTokenProvider.resolveToken(request, TokenType.ACCESS);

			if (token == null) {
				response.setHeader("jwt-error", ErrorCode.NOT_FOUND_TOKEN.name());
				filterChain.doFilter(request, response);
				return;
			}

			if (!jwtTokenProvider.isTokenValid(token)) {
				response.setHeader("jwt-error", ErrorCode.INVALID_TOKEN.name());
				filterChain.doFilter(request, response);
				return;
			}

			UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(token);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (RuntimeException e) {
			filterChain.doFilter(request, response);
			return;
		}
		filterChain.doFilter(request, response);
	}
}
