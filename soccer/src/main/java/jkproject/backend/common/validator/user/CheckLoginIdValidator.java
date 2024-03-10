package jkproject.backend.common.validator.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.backend.user.repository.UserRepository;
import jkproject.backend.user.data.dto.request.UserCreateRequestDto;
import jkproject.backend.common.exception.enums.ValidationMessage;
import jkproject.backend.common.validator.AbstractValidator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CheckLoginIdValidator extends AbstractValidator<UserCreateRequestDto> {

	private final UserRepository userRepository;

	@Override
	protected void doValidate(UserCreateRequestDto dto, Errors errors) {
		if (userRepository.existsByLoginId(dto.getLoginId())) {
			errors.rejectValue("loginId", ValidationMessage.EXISTS_LOGINID.name(),
				ValidationMessage.EXISTS_LOGINID.getMessage());
		}
	}
}
