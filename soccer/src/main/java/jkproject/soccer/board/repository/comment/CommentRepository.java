package jkproject.soccer.board.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.board.data.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryRepository {

}
