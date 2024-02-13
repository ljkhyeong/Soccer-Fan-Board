package jkproject.soccer.user.data.dto.response;

import jkproject.soccer.user.data.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserFindIdResponseDto {
	private String loginId;

	public static UserFindIdResponseDto from(User user) {
		return UserFindIdResponseDto.builder()
			.loginId(user.getLoginId())
			.build();
	}
}
