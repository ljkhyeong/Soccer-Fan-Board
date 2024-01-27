package jkproject.soccer.common.validator.board.post;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.soccer.board.data.dto.post.request.PostCreateRequestDto;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import jkproject.soccer.common.validator.AbstractValidator;

@Component
public class CreatePostValidator extends AbstractValidator<PostCreateRequestDto> {
	@Override
	protected void doValidate(PostCreateRequestDto dto, Errors errors) {
		if (!dto.isLoginState()) {
			if (dto.getTempNickname() == null || dto.getTempNickname().isBlank()) {
				errors.rejectValue("tempNickname", ValidationMessage.CANNOT_BE_NULL.name(),
					ValidationMessage.CANNOT_BE_NULL.getMessage());
			}
			if (dto.getPassword() == null || dto.getPassword().isBlank()) {
				errors.rejectValue("password", ValidationMessage.CANNOT_BE_NULL.name(),
					ValidationMessage.CANNOT_BE_NULL.getMessage());
			}
		}
	}
}
