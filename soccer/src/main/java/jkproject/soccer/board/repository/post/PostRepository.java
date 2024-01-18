package jkproject.soccer.board.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.team.data.entity.Team;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

	Page<Post> findAll(Pageable pageable);

	Page<Post> findAllByTeam(Team team, Pageable pageable);

}
