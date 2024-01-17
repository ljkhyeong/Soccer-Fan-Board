package jkproject.soccer.user.data.dto.response;

import jkproject.soccer.user.data.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponseDto {

	private String LoginId;
	private String nickname;

	public static UserCreateResponseDto from(User user) {
		return UserCreateResponseDto.builder()
			.LoginId(user.getLoginId())
			.nickname(user.getNickname())
			.build();
	}
}
