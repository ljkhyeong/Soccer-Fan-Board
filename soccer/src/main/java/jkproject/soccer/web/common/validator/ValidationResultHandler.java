package jkproject.soccer.web.common.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.soccer.web.common.exception.CustomValidationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;

@Component
public class ValidationResultHandler {

	public void ifErrorsThrow(Errors errors, ErrorCode errorCode) {
		if (errors.hasErrors()) {
			Map<String, String> validateResult = new HashMap<>();
			errors.getFieldErrors().forEach(
				(error) -> validateResult.put(String.format("valid_%s", error.getField()),
					error.getDefaultMessage()));

			throw new CustomValidationException(errorCode, validateResult);
		}
	}
}
