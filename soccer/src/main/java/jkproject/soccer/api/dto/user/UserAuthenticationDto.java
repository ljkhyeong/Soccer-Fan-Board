package jkproject.soccer.api.dto.user;

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
}
