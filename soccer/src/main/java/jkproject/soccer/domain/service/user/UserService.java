package jkproject.soccer.domain.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.api.dto.user.request.UserCreateRequestDto;
import jkproject.soccer.api.dto.user.request.UserUpdateRequestDto;
import jkproject.soccer.api.dto.user.response.UserCreateResponseDto;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.common.exception.ApplicationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import jkproject.soccer.web.common.validator.ValidationResultHandler;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	// TODO 생성자 주입, 필드 주입, 세터 주입의 차이점 장단점.
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ValidationResultHandler validationResultHandler;

	public UserCreateResponseDto createUser(UserCreateRequestDto requestDto, Errors errors) {
		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_JOIN);

		User newUser = requestDto.toEntity();
		newUser.updatePassword(passwordEncoder.encode(requestDto.getPassword()));

		User savedUser = userRepository.save(newUser);
		return UserCreateResponseDto.from(savedUser);
	}

	public void updateUser(UserUpdateRequestDto requestDto, UserAuthenticationDto userDto) {
		User foundUser = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		//TODO userDto에 longId 필드를 추가하고 찾을때마다 longId로 쓰는게 더 낫지않을까?

		foundUser.updateUserData(requestDto);
		userDto.setNickname(requestDto.getNickname());
		//TODO 변경한 사항에만 적용하도록 리팩토링 필요. 사용자는 비번 안바꿨는데 실수로 바뀔 가능성 존재
	}

	public void deleteUser(UserAuthenticationDto userDto) {
		User foundUser = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));

		userRepository.delete(foundUser);
	}

}
