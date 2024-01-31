package jkproject.soccer.board.controller.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jkproject.soccer.board.data.dto.comment.request.CommentCreateRequestDto;
import jkproject.soccer.board.data.dto.comment.request.CommentDeleteRequestDto;
import jkproject.soccer.board.data.dto.comment.response.CommentListResponseDto;
import jkproject.soccer.board.service.comment.CommentService;
import jkproject.soccer.common.data.dto.response.Response;
import jkproject.soccer.common.validator.board.comment.CreateCommentValidator;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentApiController {

	private final CommentService commentService;
	private final CreateCommentValidator createCommentValidator;

	@InitBinder
	public void createCommentValidatorBinder(WebDataBinder binder) {
		binder.addValidators(createCommentValidator);
	}

	@GetMapping("/{teamCode}/posts/{postId}/comments")
	public Response<Page<CommentListResponseDto>> readComments(@PathVariable String teamCode,
		@PathVariable Long postId, Pageable pageable) {

		Page<CommentListResponseDto> dtoList = commentService.readComments(postId, pageable);

		return Response.success(dtoList);
	}

	@PostMapping("/{teamCode}/posts/{postId}/comment")
	public Response<Void> createComment(@PathVariable String teamCode, @PathVariable Long postId,
		@RequestBody @Valid CommentCreateRequestDto createCommentRequestDto, Errors errors,
		@AuthenticationPrincipal UserAuthenticationDto userDto,
		HttpServletRequest request) {

		commentService.createComment(postId, createCommentRequestDto, userDto, errors, request);
		return Response.success();
	}

	@DeleteMapping("/{teamCode}/posts/{postId}/comment/{commentId}")
	public Response<Void> deleteComment(@PathVariable String teamCode, @PathVariable Long postId,
		@PathVariable Long commentId,
		@AuthenticationPrincipal UserAuthenticationDto userDto,
		@RequestBody CommentDeleteRequestDto requestDto) {

		commentService.deleteComment(commentId, userDto, requestDto);

		return Response.success();
	}
}
