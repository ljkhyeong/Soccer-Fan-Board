package jkproject.backend.domain.user.service;

// @SpringBootTest(classes = {UserService.class})
// @Import(value = MockProvider.class)
// @DisplayName("회원 테스트")
// public class UserServiceTest {
//
// 	@Autowired
// 	private UserService userService;
//
// 	@MockBean
// 	private UserRepository userRepository;
//
// 	private UserAuthenticationDto getMockUserDto() {
// 		return UserAuthenticationDto.from(getMockUser());
// 	}
//
// 	@Nested
// 	@DisplayName("회원가입 테스트")
// 	class AboutCreateUser {
// 		private UserCreateRequestDto getMockRequestDto() {
// 			UserCreateRequestDto dto = new UserCreateRequestDto();
// 			dto.setEmail("test@test.com");
// 			dto.setLoginId("test");
// 			dto.setPassword("password");
// 			dto.setNickname("안녕");
// 			dto.setPhoneNumber("010-2222-3333");
// 			return dto;
// 		}
//
// 		@Test
// 		@DisplayName("정상적인 회원가입 요청일 시, 새로운 회원을 생성 후 Success 반환")
// 		public void givenProperRequest_whenCreatingUser_thenCreatesNewUserAndReturnSuccess() {
//
// 			//TODO Validation 추가 후 테스트 수정 필요
//
// 			UserCreateRequestDto mockRequestDto = getMockRequestDto();
// 			User mockUser = getMockUser();
// 			given(userRepository.save(any(User.class))).willReturn(mockUser);
//
// 			UserCreateResponseDto responseDto = userService.createUser(mockRequestDto);
//
// 			then(userRepository).should().save(any(User.class));
// 			Assertions.assertThat(responseDto)
// 				.hasFieldOrPropertyWithValue("loginId", mockUser.getLoginId())
// 				.hasFieldOrPropertyWithValue("nickname", mockUser.getNickname());
// 		}
// 	}
// }
