package jkproject.soccer.board.controller.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jkproject.soccer.board.data.dto.post.request.PostAuthorityRequestDto;
import jkproject.soccer.board.data.dto.post.request.PostCreateRequestDto;
import jkproject.soccer.board.data.dto.post.request.PostDeleteRequestDto;
import jkproject.soccer.board.data.dto.post.request.PostUpdateRequestDto;
import jkproject.soccer.board.data.dto.post.request.SearchCondition;
import jkproject.soccer.board.data.dto.post.response.PostDetailResponseDto;
import jkproject.soccer.board.data.dto.post.response.PostListResponseDto;
import jkproject.soccer.board.service.post.PostService;
import jkproject.soccer.common.data.dto.response.Response;
import jkproject.soccer.common.validator.board.post.CreatePostValidator;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostApiController {

	private final PostService postService;
	private final CreatePostValidator createPostValidator;

	@InitBinder
	public void createPostValidatorBinder(WebDataBinder binder) {
		binder.addValidators(createPostValidator);
	}

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
		@RequestBody @Valid PostCreateRequestDto createRequestDto, Errors errors,
		@AuthenticationPrincipal UserAuthenticationDto userDto,
		HttpServletRequest request) {

		postService.createPost(teamCode, createRequestDto, userDto, errors, request);
		return Response.success();
	}

	@GetMapping("/{teamCode}/posts/{postId}")
	public Response<PostDetailResponseDto> readPost(@PathVariable String teamCode, @PathVariable Long postId) {
		PostDetailResponseDto responseDto = postService.readPost(postId);
		return Response.success(responseDto);
	}

	@PostMapping("/{teamCode}/posts/{postId}/permission")
	public Response<Void> checkAuthorityPost(@PathVariable String teamCode, @PathVariable Long postId,
		@AuthenticationPrincipal UserAuthenticationDto userDto,
		@RequestBody PostAuthorityRequestDto requestDto) {
		postService.checkAuthority(postId, userDto, requestDto);
		return Response.success();
	}

	@PutMapping("/{teamCode}/posts/{postId}")
	public Response<Void> updatePost(@PathVariable String teamCode, @PathVariable Long postId,
		@AuthenticationPrincipal UserAuthenticationDto userDto,
		@RequestBody @Valid PostUpdateRequestDto requestDto, Errors errors,
		HttpServletRequest request) {
		postService.updatePost(postId, userDto, requestDto, errors, request);
		return Response.success();
	}

	@PostMapping("/{teamCode}/posts/{postId}/delete")
	public Response<Void> deletePost(@PathVariable String teamCode, @PathVariable Long postId,
		@AuthenticationPrincipal UserAuthenticationDto userDto,
		@RequestBody PostDeleteRequestDto requestDto) {
		postService.deletePost(postId, userDto, requestDto);
		return Response.success();
	}
}
