package jkproject.soccer.common.util;

import org.springframework.boot.test.context.TestConfiguration;

import jkproject.soccer.domain.entity.user.User;

@TestConfiguration
public class MockProvider {

	public static User getMockUser() {
		return User.builder()
			.loginId("testUser")
			.password("password")
			.nickname("안녕")
			.email("hihi@naver.com")
			.phoneNumber("010-2222-3333")
			.build();
	}
}
