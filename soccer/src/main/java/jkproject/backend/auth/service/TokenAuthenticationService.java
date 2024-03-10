package jkproject.backend.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.backend.user.repository.UserRepository;
import jkproject.backend.user.data.dto.UserAuthenticationDto;
import jkproject.backend.user.data.entity.User;
import jkproject.backend.common.exception.ApplicationException;
import jkproject.backend.common.exception.enums.ErrorCode;
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
