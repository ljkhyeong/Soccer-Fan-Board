package jkproject.backend.auth.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jkproject.backend.auth.data.dto.request.LoginRequestDto;
import jkproject.backend.auth.data.dto.response.LoginResponseDto;
import jkproject.backend.auth.data.dto.response.RefreshResponseDto;
import jkproject.backend.common.data.dto.response.Response;
import jkproject.backend.auth.config.jwt.TokenType;
import jkproject.backend.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
	}

	@PostMapping("/login")
	public Response<Void> login(@Valid @RequestBody LoginRequestDto requestDto, Errors errors
		, HttpServletResponse response) {

		LoginResponseDto responseDto = authService.login(requestDto, errors);

		addTokenCookie(response, responseDto.getAccessToken(), TokenType.ACCESS);
		addTokenCookie(response, responseDto.getRefreshToken(), TokenType.REFRESH);

		return Response.success();
	}

	@PostMapping("/refresh")
	public Response<Void> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = parseTokenCookie(request, TokenType.REFRESH);
		RefreshResponseDto responseDto = authService.refreshAccessToken(refreshToken);

		addTokenCookie(response, responseDto.getAccessToken(), TokenType.ACCESS);
		addTokenCookie(response, responseDto.getAccessToken(), TokenType.REFRESH);

		return Response.success();
	}

	@DeleteMapping("/refresh")
	public Response<Void> disableRefreshToken(HttpServletRequest request,
		HttpServletResponse response) {
		String refreshToken = parseTokenCookie(request, TokenType.REFRESH);
		authService.disableRefreshToken(refreshToken);

		removeTokenCookie(response, TokenType.ACCESS);
		removeTokenCookie(response, TokenType.REFRESH);

		return Response.success();
	}

	private String parseTokenCookie(HttpServletRequest request, TokenType tokenType) {
		String tokenTypeName = tokenType.getName();
		Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
		return Arrays.stream(cookies)
			.filter(cookie -> tokenTypeName.equals(cookie.getName()))
			.findFirst()
			.map(Cookie::getValue)
			.orElse("");
	}

	private void addTokenCookie(HttpServletResponse response, String token, TokenType tokenType) {
		ResponseCookie tokenCookie = tokenType.createCookieFrom(token);
		response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());
	}

	private void removeTokenCookie(HttpServletResponse response, TokenType tokenType) {
		ResponseCookie removalCookie = tokenType.removalCookie();
		response.addHeader(HttpHeaders.SET_COOKIE, removalCookie.toString());
	}
}
