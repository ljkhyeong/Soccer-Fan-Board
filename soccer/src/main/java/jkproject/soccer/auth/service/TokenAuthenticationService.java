package jkproject.soccer.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.user.repository.UserRepository;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
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
