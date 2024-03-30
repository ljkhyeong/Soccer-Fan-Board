package jkproject.backend.common.service;

import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.backend.board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchService {

	private final StringRedisTemplate redisTemplate;
	private final PostRepository postRepository;

	@Scheduled(fixedDelay = 2000)
	@Transactional
	public void synchronizeViewCount() {
		Set<String> keys = redisTemplate.keys("post:viewCount:*");
		if (keys != null) {
			for (String key : keys) {
				Long postId = extractPostIdFromKey(key);
				String value = redisTemplate.opsForValue().get(key);
				if (value != null) {
					Long viewCount = Long.valueOf(value);
					postRepository.findById(postId).ifPresent(post -> post.updateViewCount(viewCount));
				}
				redisTemplate.delete(key);
			}
		}
	}

	private Long extractPostIdFromKey(String key) {
		try {
			return Long.parseLong(key.substring(key.lastIndexOf(":") + 1));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
