package jkproject.soccer.api.controller.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.auth.request.LoginRequestDto;
import jkproject.soccer.api.dto.auth.response.LoginResponseDto;
import jkproject.soccer.web.auth.config.jwt.TokenType;
import jkproject.soccer.web.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public Response<Void> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
		LoginResponseDto responseDto = authService.login(requestDto);

		addTokenCookie(response, responseDto.getAccessToken(), TokenType.ACCESS);
		addTokenCookie(response, responseDto.getRefreshToken(), TokenType.REFRESH);

		return Response.success();
	}

	private void addTokenCookie(HttpServletResponse response, String token, TokenType tokenType) {
		ResponseCookie tokenCookie = tokenType.createCookieFrom(token);
		response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());
	}
}
