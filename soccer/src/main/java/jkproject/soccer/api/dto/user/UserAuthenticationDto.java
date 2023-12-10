package jkproject.soccer.api.dto.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.enums.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class UserAuthenticationDto {

	@ToString.Include
	private String loginId;
	private String password;
	@ToString.Include
	private String nickname;
	@ToString.Include
	private String email;
	@ToString.Include
	private UserRole role;

	public static UserAuthenticationDto from(User user) {
		return UserAuthenticationDto.builder()
			.loginId(user.getLoginId())
			.password(user.getPassword())
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
