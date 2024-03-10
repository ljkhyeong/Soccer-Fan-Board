package jkproject.backend.common.validator.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.backend.common.exception.enums.ValidationMessage;
import jkproject.backend.common.validator.AbstractValidator;
import jkproject.backend.user.data.dto.request.UserUpdateRequestDto;

@Component
public class UpdateUserValidator extends AbstractValidator<UserUpdateRequestDto> {
	@Override
	protected void doValidate(UserUpdateRequestDto dto, Errors errors) {
		if (dto.getType().equals("password")) {
			if (dto.getCurrentPassword() == null || dto.getCurrentPassword().isBlank()) {
				errors.rejectValue("currentPassword", ValidationMessage.CANNOT_BE_NULL.name(),
					ValidationMessage.CANNOT_BE_NULL.getMessage());
			}
			if (dto.getNewPassword() == null || dto.getNewPassword().isBlank()) {
				errors.rejectValue("newPassword", ValidationMessage.CANNOT_BE_NULL.name(),
					ValidationMessage.CANNOT_BE_NULL.getMessage());
			}
			if (dto.getConfirmNewPassword() == null || dto.getConfirmNewPassword().isBlank()) {
				errors.rejectValue("confirmNewPassword", ValidationMessage.CANNOT_BE_NULL.name(),
					ValidationMessage.CANNOT_BE_NULL.getMessage());
			}
		}

		if (dto.getType().equals("nickname")) {
			if (dto.getNickname() == null || dto.getNickname().isBlank()) {
				errors.rejectValue("nickname", ValidationMessage.CANNOT_BE_NULL.name(),
					ValidationMessage.CANNOT_BE_NULL.getMessage());
			}
		}
	}
}
