package jkproject.soccer.web.common.validator.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jkproject.soccer.api.dto.user.request.UserCreateRequestDto;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.common.exception.enums.ValidationMessage;
import jkproject.soccer.web.common.validator.AbstractValidator;
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
