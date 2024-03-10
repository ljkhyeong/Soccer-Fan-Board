package jkproject.backend.board.repository.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jkproject.backend.board.data.entity.comment.Comment;

public interface CommentQueryRepository {

	Page<Comment> findAllByPostId(Long postId, Pageable pageable);
}
