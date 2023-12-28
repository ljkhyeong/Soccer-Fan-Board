package jkproject.soccer.api.controller.user;

import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.api.dto.user.request.UserCreateRequestDto;
import jkproject.soccer.api.dto.user.request.UserUpdateRequestDto;
import jkproject.soccer.domain.service.user.UserService;
import jkproject.soccer.web.common.validator.ValidationResultHandler;
import jkproject.soccer.web.common.validator.user.CheckLoginIdValidator;
import jkproject.soccer.web.common.validator.user.CheckNicknameValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
	private final ValidationResultHandler validationProvider;
	private final CheckLoginIdValidator checkLoginIdValidator;
	private final CheckNicknameValidator checkNicknameValidator;

	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkLoginIdValidator);
		binder.addValidators(checkNicknameValidator);
	}

	@PostMapping
	public Response<Void> createUser(@Valid @RequestBody UserCreateRequestDto requestDto,
		Errors errors) {
		userService.createUser(requestDto, errors);
		return Response.success();
	}

	@PutMapping
	public Response<Void> updateUser(@RequestBody UserUpdateRequestDto requestDto, UserAuthenticationDto userDto) {
		userService.updateUser(requestDto, userDto);
		return Response.success();
	}

	@DeleteMapping
	public Response<Void> deleteUser(UserAuthenticationDto userDto) {
		userService.deleteUser(userDto);
		return Response.success();
	}

}
