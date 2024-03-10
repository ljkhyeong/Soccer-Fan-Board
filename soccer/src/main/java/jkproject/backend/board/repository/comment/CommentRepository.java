package jkproject.backend.board.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.backend.board.data.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryRepository {

}
