package jkproject.soccer.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.user.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByLoginId(String loginId);

	Optional<User> findByNicknameAndEmail(String nickname, String email);

	Optional<User> findByEmail(String email);

	boolean existsByLoginId(String loginId);

	boolean existsByNickname(String nickname);
}
