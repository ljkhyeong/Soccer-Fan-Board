package jkproject.backend.board.repository.heart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.backend.board.data.entity.heart.Heart;
import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.user.data.entity.User;

public interface HeartRepository extends JpaRepository<Heart, Long> {

	Optional<Heart> findByUserAndPost(User user, Post post);

	boolean existsByUserAndPost(User user, Post post);

	long countByPost(Post post);

}
