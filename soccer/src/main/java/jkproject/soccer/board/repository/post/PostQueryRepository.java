package jkproject.soccer.board.repository.post;

import jkproject.soccer.board.data.entity.post.Post;

public interface PostQueryRepository {
	void updateHeartCount(Post post, boolean notClicked);

}
