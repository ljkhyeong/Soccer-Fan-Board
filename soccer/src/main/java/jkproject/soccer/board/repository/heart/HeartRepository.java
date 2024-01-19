package jkproject.soccer.board.repository.heart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.board.data.entity.heart.Heart;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.user.data.entity.User;

public interface HeartRepository extends JpaRepository<Heart, Long> {

	Optional<Heart> findByUserAndPost(User user, Post post);

	boolean existsByUserAndPost(User user, Post post);

	long countByPost(Post post);

}
