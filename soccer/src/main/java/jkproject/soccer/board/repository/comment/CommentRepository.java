package jkproject.soccer.board.repository.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jkproject.soccer.board.data.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("select c from Comment c where c.post.postId = :postId")
	Page<Comment> findByPostId(Long postId, Pageable pageable);
}
