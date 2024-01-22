package jkproject.soccer.board.controller.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jkproject.soccer.board.data.dto.post.request.PostCreateRequestDto;
import jkproject.soccer.board.data.dto.post.request.SearchCondition;
import jkproject.soccer.board.data.dto.post.response.PostDetailResponseDto;
import jkproject.soccer.board.data.dto.post.response.PostListResponseDto;
import jkproject.soccer.board.service.post.PostService;
import jkproject.soccer.common.data.dto.response.Response;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostApiController {

	private final PostService postService;

	@GetMapping("/{teamCode}/posts")
	public Response<Page<PostListResponseDto>> lookupAllPosts(@PathVariable String teamCode,
		@ModelAttribute SearchCondition condition,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<PostListResponseDto> postDtoList = postService.lookupAllPosts(teamCode, condition, pageable);
		return Response.success(postDtoList);
	}

	@GetMapping("/{teamCode}/posts/best")
	public Response<Page<PostListResponseDto>> lookupBestPosts(@PathVariable String teamCode,
		@ModelAttribute SearchCondition condition,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<PostListResponseDto> postDtoList = postService.lookUpBestPosts(teamCode, condition, pageable);
		return Response.success(postDtoList);
	}

	@PostMapping("/{teamCode}/posts")
	public Response<Void> createPost(@PathVariable String teamCode,
		@RequestBody @Valid PostCreateRequestDto requestDto,
		Errors errors, @AuthenticationPrincipal UserAuthenticationDto userDto) {
		//TODO 회원가입 상태면 회원 닉네임, 아니면 IP와 임시닉네임을 사용하도록
		postService.createPost(teamCode, requestDto, userDto, errors);
		return Response.success();
	}

	@GetMapping("/{teamCode}/posts/{postId}")
	public Response<PostDetailResponseDto> readPost(@PathVariable String teamCode, @PathVariable Long postId) {
		PostDetailResponseDto responseDto = postService.readPost(postId);
		return Response.success(responseDto);
	}
}
