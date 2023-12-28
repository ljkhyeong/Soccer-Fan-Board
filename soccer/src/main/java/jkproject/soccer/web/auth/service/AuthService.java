package jkproject.soccer.web.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.api.dto.auth.request.LoginRequestDto;
import jkproject.soccer.api.dto.auth.response.LoginResponseDto;
import jkproject.soccer.api.dto.auth.response.RefreshResponseDto;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.auth.config.jwt.JwtTokenProvider;
import jkproject.soccer.web.auth.config.jwt.TokenType;
import jkproject.soccer.web.auth.repository.RefreshTokenRepository;
import jkproject.soccer.web.common.exception.ApplicationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import jkproject.soccer.web.common.validator.ValidationResultHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;
	private final ValidationResultHandler validationResultHandler;

	public LoginResponseDto login(LoginRequestDto requestDto, Errors errors) {
		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_LOGIN);

		User user = validateLoginIdAndPassword(requestDto);
		String loginId = user.getLoginId();

		String accessToken = jwtTokenProvider.generateToken(loginId, TokenType.ACCESS);
		String refreshToken = refreshTokenRepository.findRefreshTokenByLoginId(loginId)
			.orElseGet(() -> createAndSaveRefreshToken(loginId));

		return LoginResponseDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public RefreshResponseDto refreshAccessToken(String refreshToken) {
		if (refreshToken.isBlank()) {
			throw new ApplicationException(ErrorCode.NOT_FOUND_TOKEN);
		}

		String loginId = jwtTokenProvider.getSubject(refreshToken);
		refreshTokenRepository.findRefreshTokenByLoginId(loginId)
			.filter(foundRefreshToken -> foundRefreshToken.equals(refreshToken))
			.orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

		String accessToken = jwtTokenProvider.generateToken(loginId, TokenType.ACCESS);

		return RefreshResponseDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	@Transactional
	public void disableRefreshToken(String refreshToken) {
		if (refreshToken.isBlank()) {
			throw new ApplicationException(ErrorCode.NOT_FOUND_TOKEN);
		}
		String loginId = jwtTokenProvider.getSubject(refreshToken);
		refreshTokenRepository.deleteByLoginId(loginId);
	}

	private User validateLoginIdAndPassword(LoginRequestDto requestDto) {
		User user = userRepository.findByLoginId(requestDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));

		if (passwordEncoder.matches(user.getPassword(), requestDto.getPassword())) {
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
		}
		return user;
	}

	private String createAndSaveRefreshToken(String loginId) {
		String refreshToken = jwtTokenProvider.generateToken(loginId, TokenType.REFRESH);
		refreshTokenRepository.save(loginId, refreshToken);
		return refreshToken;
	}

}
