package jkproject.soccer.board.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.team.data.entity.Team;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAll(Pageable pageable);

	Page<Post> findAllByTeam(Team team, Pageable pageable);

	@Modifying
	@Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.postId = :postId")
	void increaseViewCountByPostId(Long postId);
}
