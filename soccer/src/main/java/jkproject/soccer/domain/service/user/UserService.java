package jkproject.soccer.domain.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
 // TODO 생성자 주입, 필드 주입, 세터 주입의 차이점 장단점.


}
