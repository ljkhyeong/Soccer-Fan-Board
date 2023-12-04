package jkproject.soccer.domain.repository.board.post;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.domain.entity.board.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
