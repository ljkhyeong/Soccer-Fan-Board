package jkproject.backend.common.exception;

import java.util.Map;

import jkproject.backend.common.exception.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CustomValidationException extends ApplicationException {

	private final Map<String, String> validationMessages;

	public CustomValidationException(ErrorCode errorCode, Map<String, String> validMessages) {
		super(errorCode);
		this.validationMessages = validMessages;
	}
}
