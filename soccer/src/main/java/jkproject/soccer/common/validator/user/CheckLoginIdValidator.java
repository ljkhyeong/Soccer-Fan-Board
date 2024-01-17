package jkproject.soccer.common.validator.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.soccer.user.repository.UserRepository;
import jkproject.soccer.user.data.dto.request.UserCreateRequestDto;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import jkproject.soccer.common.validator.AbstractValidator;
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
