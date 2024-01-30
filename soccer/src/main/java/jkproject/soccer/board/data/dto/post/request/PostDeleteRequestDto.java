package jkproject.soccer.board.data.dto.post.request;

import lombok.Data;

@Data
public class PostDeleteRequestDto {
	private boolean nonUserPost;
	private String password;
}
