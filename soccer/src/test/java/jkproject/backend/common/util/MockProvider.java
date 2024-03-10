package jkproject.backend.common.util;

import org.springframework.boot.test.context.TestConfiguration;

import jkproject.backend.user.data.entity.User;

@TestConfiguration
public class MockProvider {

	public static User getMockUser() {
		return User.builder()
			.loginId("test")
			.password("password")
			.nickname("안녕")
			.email("test@test.com")
			.phoneNumber("010-2222-3333")
			.build();
	}
}
