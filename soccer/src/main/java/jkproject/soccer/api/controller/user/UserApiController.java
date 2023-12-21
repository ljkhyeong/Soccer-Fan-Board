package jkproject.soccer.api.controller.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.api.dto.user.request.UserCreateRequestDto;
import jkproject.soccer.api.dto.user.request.UserUpdateRequestDto;
import jkproject.soccer.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;

	@PostMapping
	public Response<Void> createUser(@RequestBody UserCreateRequestDto requestDto) {
		userService.createUser(requestDto);
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
