package jkproject.soccer.board.data.dto.heart.request;

import jkproject.soccer.board.data.entity.heart.Heart;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.user.data.entity.User;
import lombok.Data;

@Data
public class HeartCreateRequestDto {
	private boolean notHeart;

	public Heart toEntity(User user, Post post) {
		return Heart.builder()
			.notHeart(notHeart)
			.user(user)
			.post(post)
			.build();
	}
}
