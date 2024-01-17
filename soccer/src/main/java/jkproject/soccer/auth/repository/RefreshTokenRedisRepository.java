package jkproject.soccer.auth.repository;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RefreshTokenRedisRepository implements RefreshTokenRepository {

	private final RedisTemplate<String, String> redisTemplate;
	private final Duration ROW_DURATION = Duration.ofMinutes(30);
	private final String REDIS_PREFIX = "REFRESH_TOKEN:";

	private String makeKeyFrom(String loginId) {
		return REDIS_PREFIX + loginId;
	}

	@Override
	public void save(String loginId, String refreshToken) {
		String key = makeKeyFrom(loginId);
		redisTemplate.opsForValue().set(key, refreshToken, ROW_DURATION);
	}

	@Override
	public Optional<String> findRefreshTokenByLoginId(String loginId) {
		String key = makeKeyFrom(loginId);
		String refreshToken = redisTemplate.opsForValue().get(key);
		return Optional.ofNullable(refreshToken);
	}

	@Override
	public void deleteByLoginId(String loginId) {
		String key = makeKeyFrom(loginId);
		redisTemplate.delete(key);
	}
}
