package jkproject.backend.board.service.post.view;

import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.board.repository.post.PostRepository;
import jkproject.backend.common.exception.ApplicationException;
import jkproject.backend.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewCountService {

	private final StringRedisTemplate redisTemplate;
	private final PostRepository postRepository;

	@Async
	public void increaseViewCount(Long postId) {
		String key = "post:viewCount:" + postId;
		redisTemplate.opsForValue().increment(key, 1);
	}

	@Transactional(readOnly = true)
	public Long getViewCount(Long postId) {
		String key = "post:viewCount:" + postId;
		Optional<String> value = Optional.ofNullable(redisTemplate.opsForValue().get(key));

		return Long.valueOf(value.orElseGet(() -> String.valueOf(postRepository.findById(postId)
			.map(Post::getViewCount)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID)))));
	}
}
