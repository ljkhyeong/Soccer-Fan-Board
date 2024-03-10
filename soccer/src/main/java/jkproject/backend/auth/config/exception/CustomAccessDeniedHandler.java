package jkproject.backend.auth.config.exception;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jkproject.backend.common.data.dto.response.Response;
import jkproject.backend.common.exception.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.info(accessDeniedException.getMessage() +
			" for request of URI: [" + request.getRequestURI() + "]" +
			" requested by Account: " + request.getUserPrincipal().getName());
		ErrorCode errorCode = ErrorCode.AUTHORIZATION_ERROR;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(errorCode.getHttpStatus().value());
		response.getWriter().write(Response.error(errorCode.name(), errorCode.getMessage()).toStream());
	}

}
