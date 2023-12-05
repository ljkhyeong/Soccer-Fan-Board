package jkproject.soccer.api.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.user.request.UserCreateRequestDto;
import jkproject.soccer.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;

	@PostMapping
	public Response<Void> createUser(@RequestBody UserCreateRequestDto requestDto) {
		userService.createUser(requestDto);
		return Response.success();
	}

}
