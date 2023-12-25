package jkproject.soccer.web.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.common.exception.ApplicationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenAuthenticationService {

	private final UserRepository userRepository;

	public UserAuthenticationDto getUserAuthenticationDto(String loginId) {
		User user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));

		return UserAuthenticationDto.from(user);
	}
}
