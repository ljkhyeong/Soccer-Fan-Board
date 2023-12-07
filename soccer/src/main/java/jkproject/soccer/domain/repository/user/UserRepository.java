package jkproject.soccer.domain.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.domain.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByLoginId(String loginId);

	boolean existsByEmail(String email);

}
