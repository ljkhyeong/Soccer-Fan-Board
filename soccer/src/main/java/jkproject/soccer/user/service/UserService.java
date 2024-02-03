package jkproject.soccer.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
import jkproject.soccer.common.validator.ValidationResultHandler;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import jkproject.soccer.user.data.dto.request.UserCreateRequestDto;
import jkproject.soccer.user.data.dto.request.UserUpdateRequestDto;
import jkproject.soccer.user.data.dto.response.UserCreateResponseDto;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.user.repository.UserRepository;
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

	public void updateUser(UserUpdateRequestDto requestDto, Errors errors, UserAuthenticationDto userDto) {
		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_UPDATE_USER);
		//TODO userDto에 longId 필드를 추가하고 찾을때마다 longId로 쓰는게 더 낫지않을까?
		User user = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));

		if (requestDto.getType().equals("password")) {
			if (passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
				throw new ApplicationException(ErrorCode.NOT_EQUALS_CURRENT_PASSWORD);
			}
			if (!requestDto.getNewPassword().equals(requestDto.getConfirmNewPassword())) {
				throw new ApplicationException(ErrorCode.NOT_EQUALS_CONFIRM_PASSWORD);
			}
			user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
		}
		
		//TODO 캡챠도 넣자.
	}

	public void deleteUser(UserAuthenticationDto userDto) {
		User foundUser = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));

		userRepository.delete(foundUser);
	}

}
