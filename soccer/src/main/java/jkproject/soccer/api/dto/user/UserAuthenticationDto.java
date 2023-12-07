package jkproject.soccer.api.dto.user;

import jkproject.soccer.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class UserAuthenticationDto {

	private Long userId;
	@ToString.Include
	private String loginId;
	@ToString.Include
	private String nickname;
	@ToString.Include
	private String email;

	@Builder
	private UserAuthenticationDto(String loginId, String nickname, String email) {
		this.loginId = loginId;
		this.nickname = nickname;
		this.email = email;
	}

	public static UserAuthenticationDto from(User user) {
		return UserAuthenticationDto.builder()
			.loginId(user.getLoginId())
			.nickname(user.getNickname())
			.email(user.getEmail())
			.build();
	}

}
