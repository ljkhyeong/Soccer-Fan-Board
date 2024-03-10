package jkproject.backend.user.data.dto.response;

import jkproject.backend.user.data.entity.User;
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
