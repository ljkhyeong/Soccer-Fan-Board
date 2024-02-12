package jkproject.soccer.user.data.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.user.data.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class UserAuthenticationDto {

	private Long userId;
	@ToString.Include
	private String loginId;
	@ToString.Include
	private String nickname;
	@ToString.Include
	private String email;
	@ToString.Include
	private UserRole role;

	public static UserAuthenticationDto from(User user) {
		return UserAuthenticationDto.builder()
			.userId(user.getUserId())
			.loginId(user.getLoginId())
			.nickname(user.getNickname())
			.email(user.getEmail())
			.build();
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add((GrantedAuthority)() -> role.getName());
		return authorities;
	}
}
