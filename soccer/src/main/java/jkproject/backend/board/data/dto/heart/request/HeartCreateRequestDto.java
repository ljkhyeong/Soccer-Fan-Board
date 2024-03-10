package jkproject.backend.board.data.dto.heart.request;

import jkproject.backend.board.data.entity.heart.Heart;
import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.user.data.entity.User;
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
