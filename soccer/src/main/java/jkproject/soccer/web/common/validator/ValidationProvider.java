package jkproject.soccer.web.common.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ValidationProvider {

	public Map<String, String> validationResult(Errors errors) {
		Map<String, String> validateResult = new HashMap<>();
		errors.getFieldErrors().forEach(
			(error) -> validateResult.put(String.format("valid_%s", error.getField()),
				error.getDefaultMessage()));

		return validateResult;
	}
}
