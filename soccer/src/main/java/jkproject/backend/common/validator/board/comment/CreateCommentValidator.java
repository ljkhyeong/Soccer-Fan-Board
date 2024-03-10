package jkproject.backend.common.validator.board.comment;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.backend.board.data.dto.comment.request.CommentCreateRequestDto;
import jkproject.backend.common.exception.enums.ValidationMessage;
import jkproject.backend.common.validator.AbstractValidator;

@Component
public class CreateCommentValidator extends AbstractValidator<CommentCreateRequestDto> {

	@Override
	public boolean supports(Class<?> clazz) {
		return CommentCreateRequestDto.class.isAssignableFrom(clazz);
	}

	@Override
	protected void doValidate(CommentCreateRequestDto dto, Errors errors) {
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
