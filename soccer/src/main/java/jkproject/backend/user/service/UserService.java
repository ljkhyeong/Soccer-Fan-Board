package jkproject.backend.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.backend.common.exception.ApplicationException;
import jkproject.backend.common.exception.enums.ErrorCode;
import jkproject.backend.common.service.MailService;
import jkproject.backend.common.util.CommonUtils;
import jkproject.backend.common.validator.ValidationExceptionThrower;
import jkproject.backend.user.data.dto.UserAuthenticationDto;
import jkproject.backend.user.data.dto.request.UserCreateRequestDto;
import jkproject.backend.user.data.dto.request.UserFindIdRequestDto;
import jkproject.backend.user.data.dto.request.UserFindPasswordRequestDto;
import jkproject.backend.user.data.dto.request.UserUpdateRequestDto;
import jkproject.backend.user.data.dto.response.UserCreateResponseDto;
import jkproject.backend.user.data.dto.response.UserFindIdResponseDto;
import jkproject.backend.user.data.entity.User;
import jkproject.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	// TODO 생성자 주입, 필드 주입, 세터 주입의 차이점 장단점.
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ValidationExceptionThrower validationExceptionThrower;
	private final MailService mailService;

	public UserCreateResponseDto createUser(UserCreateRequestDto requestDto, Errors errors) {
		validationExceptionThrower.ifErrorsThrow(errors, ErrorCode.INVALID_JOIN);

		User newUser = requestDto.toEntity();
		newUser.updatePassword(passwordEncoder.encode(requestDto.getPassword()));

		User savedUser = userRepository.save(newUser);
		return UserCreateResponseDto.from(savedUser);
	}

	public void updateUser(UserUpdateRequestDto requestDto, Errors errors, UserAuthenticationDto userDto) {
		validationExceptionThrower.ifErrorsThrow(errors, ErrorCode.INVALID_UPDATE_USER);
		//TODO userDto에 longId 필드를 추가하고 찾을때마다 longId로 쓰는게 더 낫지않을까?
		User user = userRepository.findById(userDto.getUserId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));

		if (requestDto.getType().equals("password")) {
			if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
				throw new ApplicationException(ErrorCode.NOT_EQUALS_CURRENT_PASSWORD);
			}
			if (!requestDto.getNewPassword().equals(requestDto.getConfirmNewPassword())) {
				throw new ApplicationException(ErrorCode.NOT_EQUALS_CONFIRM_PASSWORD);
			}
			user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
		}

		if (requestDto.getType().equals("nickname")) {
			if (userRepository.existsByNickname(requestDto.getNickname())) {
				throw new ApplicationException(ErrorCode.ALREADY_EXISTENT_NICKNAME);
			}
			user.updateNickname(requestDto.getNickname());
		}

		//TODO 캡챠도 넣자.
	}

	public void deleteUser(UserAuthenticationDto userDto) {
		User foundUser = userRepository.findById(userDto.getUserId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));

		userRepository.delete(foundUser);
	}

	@Transactional(readOnly = true)
	public UserFindIdResponseDto findLoginId(UserFindIdRequestDto requestDto, Errors errors) {
		validationExceptionThrower.ifErrorsThrow(errors, ErrorCode.INVALID_SEARCH_USER);

		User user = userRepository.findByNicknameAndEmail(requestDto.getNickname(), requestDto.getEmail())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_BY_NICKNAME_AND_EMAIL));

		return UserFindIdResponseDto.from(user);
	}

	public void sendTempPassword(UserFindPasswordRequestDto requestDto, Errors errors) {
		validationExceptionThrower.ifErrorsThrow(errors, ErrorCode.INVALID_SEND_MAIL);

		String email = requestDto.getEmail();
		String tempPassword = CommonUtils.createTempPassword();

		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_BY_EMAIL));
		user.updatePassword(passwordEncoder.encode(tempPassword));

		mailService.sendMessage(email, tempPassword);
	}
}
