package jkproject.soccer.domain.service.board.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.api.dto.board.comment.response.CommentListResponseDto;
import jkproject.soccer.domain.entity.board.comment.Comment;
import jkproject.soccer.domain.repository.board.comment.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	public Page<CommentListResponseDto> readComments(Long postId, Pageable pageable) {
		Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
		return comments.map(CommentListResponseDto::from);
	}

}
