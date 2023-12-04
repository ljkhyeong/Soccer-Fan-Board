package jkproject.soccer.domain.repository.board.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.domain.entity.board.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
