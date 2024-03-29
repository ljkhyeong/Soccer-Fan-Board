package jkproject.backend.common.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jkproject.backend.common.data.dto.response.Response;
import jkproject.backend.common.exception.enums.ErrorCode;
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

	@ExceptionHandler(CustomValidationException.class)
	public ResponseEntity<?> handleValidationException(CustomValidationException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		Map<String, String> validationMessages = exception.getValidationMessages();
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(Response.error(errorCode.name(), validationMessages));
	}
}
