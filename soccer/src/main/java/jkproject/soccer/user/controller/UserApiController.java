package jkproject.soccer.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jkproject.soccer.common.data.dto.response.Response;
import jkproject.soccer.common.validator.user.CheckLoginIdValidator;
import jkproject.soccer.common.validator.user.CheckNicknameValidator;
import jkproject.soccer.common.validator.user.UpdateUserValidator;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import jkproject.soccer.user.data.dto.request.UserCreateRequestDto;
import jkproject.soccer.user.data.dto.request.UserFindIdRequestDto;
import jkproject.soccer.user.data.dto.request.UserUpdateRequestDto;
import jkproject.soccer.user.data.dto.response.UserFindIdResponseDto;
import jkproject.soccer.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
	private final CheckLoginIdValidator checkLoginIdValidator;
	private final CheckNicknameValidator checkNicknameValidator;
	private final UpdateUserValidator updateUserValidator;

	@InitBinder("userCreateRequestDto")
	public void createValidatorBinder(WebDataBinder binder) {
		binder.addValidators(checkLoginIdValidator);
		binder.addValidators(checkNicknameValidator);
	}

	@InitBinder("userUpdateRequestDto")
	public void updateValidatorBinder(WebDataBinder binder) {
		binder.addValidators(updateUserValidator);
	}

	@PostMapping
	public Response<Void> createUser(@Valid @RequestBody UserCreateRequestDto requestDto,
		Errors errors) {
		userService.createUser(requestDto, errors);
		return Response.success();
	}

	@PutMapping
	public Response<Void> updateUser(@Valid @RequestBody UserUpdateRequestDto requestDto, Errors errors,
		@AuthenticationPrincipal UserAuthenticationDto userDto) {
		userService.updateUser(requestDto, errors, userDto);
		return Response.success();
	}

	@GetMapping("/findId")
	public Response<UserFindIdResponseDto> findLoginId(@Valid @RequestBody UserFindIdRequestDto requestDto,
		Errors errors) {
		UserFindIdResponseDto responseDto = userService.findLoginId(requestDto, errors);
		return Response.success(responseDto);
	}

	@DeleteMapping
	public Response<Void> deleteUser(UserAuthenticationDto userDto) {
		userService.deleteUser(userDto);
		return Response.success();
	}

}
