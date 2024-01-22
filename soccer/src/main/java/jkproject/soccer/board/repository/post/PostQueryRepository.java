package jkproject.soccer.board.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jkproject.soccer.board.data.dto.post.request.SearchCondition;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.team.data.entity.Team;

public interface PostQueryRepository {
	void updateHeartCount(Post post, boolean notClicked);

	Page<Post> findAllByTeam(Team team, SearchCondition condition, Pageable pageable);
}
