package jkproject.soccer.board.repository.heart;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.board.data.entity.heart.Heart;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.user.data.entity.User;

public interface HeartRepository extends JpaRepository<Heart, Long>, HeartQueryRepository {

	boolean existsByUserAndPost(User user, Post post);
}
