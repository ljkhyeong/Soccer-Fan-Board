package jkproject.soccer.web.auth.service;

import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.api.dto.auth.request.LoginRequestDto;
import jkproject.soccer.api.dto.auth.response.LoginResponseDto;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.auth.config.jwt.JwtTokenProvider;
import jkproject.soccer.web.auth.config.jwt.TokenType;
import jkproject.soccer.web.auth.repository.RefreshTokenRepository;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	public LoginResponseDto login(LoginRequestDto requestDto) {

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

	private User validateLoginIdAndPassword(LoginRequestDto requestDto) {
		User user = userRepository.findByLoginId(requestDto.getLoginId())
			.orElseThrow(() -> new ApplicationContextException(ErrorCode.NON_EXISTENT_USER_ID.getMessage()));

		if (passwordEncoder.matches(user.getPassword(), requestDto.getPassword())) {
			throw new ApplicationContextException(ErrorCode.INVALID_PASSWORD.getMessage());
		}
		return user;
	}

	private String createAndSaveRefreshToken(String loginId) {
		String refreshToken = jwtTokenProvider.generateToken(loginId, TokenType.REFRESH);
		refreshTokenRepository.save(loginId, refreshToken);
		return refreshToken;
	}
}
