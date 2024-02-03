package jkproject.soccer.common.validator.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.soccer.common.exception.enums.ValidationMessage;
import jkproject.soccer.common.validator.AbstractValidator;
import jkproject.soccer.user.data.dto.request.UserUpdateRequestDto;

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
