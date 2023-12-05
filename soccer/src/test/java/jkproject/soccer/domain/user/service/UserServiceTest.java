package jkproject.soccer.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import jkproject.soccer.api.dto.user.request.UserCreateRequestDto;
import jkproject.soccer.common.util.MockProvider;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.domain.service.user.UserService;

@SpringBootTest(classes = {UserService.class})
@Import(value = MockProvider.class)
@DisplayName("회원 테스트")
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Nested
	@DisplayName("회원가입 테스트")
	class AboutCreateUser {
		private UserCreateRequestDto dto = new UserCreateRequestDto();
	}
}
