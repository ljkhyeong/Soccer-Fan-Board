package jkproject.soccer.api.controller.board.comment;

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
import jkproject.soccer.api.dto.board.comment.request.CommentCreateRequestDto;
import jkproject.soccer.api.dto.board.comment.response.CommentListResponseDto;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.domain.service.board.comment.CommentService;
import jkproject.soccer.web.common.exception.CustomValidationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import jkproject.soccer.web.common.validator.ValidationProvider;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentApiController {

	private final CommentService commentService;
	private final ValidationProvider validationProvider;

	@GetMapping("/post/{postId}/comments")
	public Response<Page<CommentListResponseDto>> readComments(@PathVariable Long postId,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<CommentListResponseDto> dtoList = commentService.readComments(postId, pageable);

		return Response.success(dtoList);
	}

	@PostMapping("/post/{postId}/comment")
	public Response<Void> createComment(@PathVariable Long postId,
		@RequestBody @Valid CommentCreateRequestDto requestDto, Errors errors,
		@AuthenticationPrincipal UserAuthenticationDto userDto) {

		if (errors.hasErrors()) {
			Map<String, String> validationResult = validationProvider.validationResult(errors);
			throw new CustomValidationException(ErrorCode.INVALID_CREATE_COMMENT, validationResult);
		}
		// TODO ID 혹은 IP가 뜨도록
		commentService.createComment(postId, requestDto, userDto);

		return Response.success();
	}
}
