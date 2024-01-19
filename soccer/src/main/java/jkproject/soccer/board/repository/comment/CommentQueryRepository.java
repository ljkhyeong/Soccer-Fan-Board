package jkproject.soccer.board.repository.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jkproject.soccer.board.data.entity.comment.Comment;

public interface CommentQueryRepository {

	Page<Comment> findAllByPostId(Long postId, Pageable pageable);
}
