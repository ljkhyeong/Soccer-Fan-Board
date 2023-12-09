package jkproject.soccer.api.controller.board.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.board.comment.response.CommentListResponseDto;
import jkproject.soccer.domain.service.board.comment.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentApiController {

	private final CommentService commentService;

	@GetMapping("/post/{postId}/comments")
	public Response<Page<CommentListResponseDto>> readComments(@PathVariable Long postId,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<CommentListResponseDto> dtoList = commentService.readComments(postId, pageable);

		return Response.success(dtoList);
	}
}
