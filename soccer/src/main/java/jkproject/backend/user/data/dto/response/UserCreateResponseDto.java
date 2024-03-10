package jkproject.backend.user.data.dto.response;

import jkproject.backend.user.data.entity.User;
import lombok.Builder;
import lombok.Data;

// 테스트코드용
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
