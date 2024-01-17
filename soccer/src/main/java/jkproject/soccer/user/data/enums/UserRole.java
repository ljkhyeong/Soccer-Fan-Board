package jkproject.soccer.user.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
	NON_USER("ROLE_NONE_USER"),
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String name;
}
