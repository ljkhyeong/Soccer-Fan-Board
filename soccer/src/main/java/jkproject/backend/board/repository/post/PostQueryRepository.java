package jkproject.backend.board.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jkproject.backend.board.data.dto.post.request.SearchCondition;
import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.team.data.entity.Team;

public interface PostQueryRepository {
	void updateHeartCount(Post post, boolean notHeart);

	Page<Post> findAllByTeam(Team team, SearchCondition condition, Pageable pageable);

	Page<Post> findBestAllByTeam(Team team, SearchCondition condition, Pageable pageable);
}
