package jkproject.soccer.board.repository.heart;

import jkproject.soccer.board.data.entity.post.Post;

public interface HeartQueryRepository {
	void updateHeartCount(Post post, boolean notClicked);
}
