package jkproject.soccer.api.controller.board.post;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.board.post.request.PostCreateRequestDto;
import jkproject.soccer.api.dto.board.post.response.PostDetailResponseDto;
import jkproject.soccer.api.dto.board.post.response.PostListResponseDto;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.domain.service.board.post.PostService;
import jkproject.soccer.web.common.exception.CustomValidationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import jkproject.soccer.web.common.validator.ValidationProvider;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostApiController {

	private final PostService postService;
	private final ValidationProvider validationProvider;

	@GetMapping("/posts")
	public Response<Page<PostListResponseDto>> lookupAllPosts(@PageableDefault(sort = "createdAt",
		direction = Sort.Direction.DESC) Pageable pageable) {
		Page<PostListResponseDto> postDtoList = postService.lookupAllPosts(pageable);
		return Response.success(postDtoList);
	}

	@PostMapping("/post")
	public Response<Void> createPost(@RequestBody @Valid PostCreateRequestDto requestDto,
		Errors errors, @AuthenticationPrincipal UserAuthenticationDto userDto) {

		if (errors.hasErrors()) {
			Map<String, String> validationResult = validationProvider.validationResult(errors);
			throw new CustomValidationException(ErrorCode.INVALID_CREATE_POST, validationResult);
		}
		//TODO 회원가입 상태면 회원 닉네임, 아니면 IP와 임시닉네임을 사용하도록
		postService.createPost(requestDto, userDto);
		return Response.success();
	}

	@GetMapping("/post/{postId}")
	public Response<PostDetailResponseDto> readPost(@PathVariable Long postId) {
		PostDetailResponseDto responseDto = postService.readPost(postId);
		return Response.success(responseDto);
	}
}
