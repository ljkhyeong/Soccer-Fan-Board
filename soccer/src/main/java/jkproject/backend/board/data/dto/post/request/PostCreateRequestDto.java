package jkproject.backend.board.data.dto.post.request;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.common.exception.enums.ValidationMessage;
import jkproject.backend.team.data.entity.Team;
import jkproject.backend.user.data.entity.User;
import lombok.Data;

@Data
public class PostCreateRequestDto {

	private boolean loginState;
	private String tempNickname;
	private String password;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String title;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String content;

	public Post toEntity(Team team, @Nullable User user, String ipAddress) {
		// TODO 로컬테스트 환경에서는 IPv6 반환 -> IPv4로 코드 수정 필요
		String writerNickname = loginState ? user.getNickname() :
			(tempNickname + ("(" + ipAddress.split("\\.")[0] + ipAddress.split("\\.")[1]) + ")");
		return Post.builder()
			.title(title)
			.content(content)
			.writer(writerNickname)
			.ipAddress(ipAddress)
			.team(team)
			.user(user)
			.build();
	}

}
