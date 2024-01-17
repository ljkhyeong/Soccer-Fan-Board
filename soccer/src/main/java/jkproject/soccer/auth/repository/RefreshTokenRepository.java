package jkproject.soccer.auth.repository;

import java.util.Optional;

public interface RefreshTokenRepository {
	void save(String loginId, String refreshToken);

	Optional<String> findRefreshTokenByLoginId(String loginId);

	void deleteByLoginId(String loginId);
}
