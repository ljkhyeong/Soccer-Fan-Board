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
public class CheckNicknameValidator extends AbstractValidator<UserCreateRequestDto> {

	private final UserRepository userRepository;

	@Override
	protected void doValidate(UserCreateRequestDto dto, Errors errors) {
		if (userRepository.existsByNickname(dto.getNickname())) {
			errors.rejectValue("nickname", ValidationMessage.EXISTS_NICKNAME.name(),
				ValidationMessage.EXISTS_NICKNAME.getMessage());
		}
	}
}
