package jkproject.soccer.auth.config.exception;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jkproject.soccer.common.data.dto.response.Response;
import jkproject.soccer.common.exception.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Map<String, ErrorCode> authErrorMap = Stream.of(ErrorCode.NOT_FOUND_TOKEN,
			ErrorCode.INVALID_TOKEN)
		.collect(Collectors.toUnmodifiableMap(ErrorCode::name, e -> e));

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		ErrorCode errorCode = resolveErrorCode(response.getHeader("jwt-error"));
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(errorCode.getHttpStatus().value());
		response.getWriter().write(Response.error(errorCode.name(), errorCode.getMessage()).toStream());
	}

	private ErrorCode resolveErrorCode(String errorName) {
		return authErrorMap.getOrDefault(errorName, ErrorCode.AUTHENTICATION_ERROR);
	}
}
