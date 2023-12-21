package jkproject.soccer.web.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<?> handleApplicationException(ApplicationException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(Response.error(errorCode.name(), errorCode.getMessage()));
	}
}
