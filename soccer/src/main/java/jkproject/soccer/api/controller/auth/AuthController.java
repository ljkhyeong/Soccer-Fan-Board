package jkproject.soccer.api.controller.auth;

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
import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.auth.request.LoginRequestDto;
import jkproject.soccer.api.dto.auth.response.LoginResponseDto;
import jkproject.soccer.api.dto.auth.response.RefreshResponseDto;
import jkproject.soccer.web.auth.config.jwt.JwtTokenProvider;
import jkproject.soccer.web.auth.config.jwt.TokenType;
import jkproject.soccer.web.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;

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
