package jkproject.backend.common.validator.board.post;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.backend.board.data.dto.post.request.PostCreateRequestDto;
import jkproject.backend.common.exception.enums.ValidationMessage;
import jkproject.backend.common.validator.AbstractValidator;

@Component
public class CreatePostValidator extends AbstractValidator<PostCreateRequestDto> {

	@Override
	public boolean supports(Class<?> clazz) {
		return PostCreateRequestDto.class.isAssignableFrom(clazz);
	}

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
