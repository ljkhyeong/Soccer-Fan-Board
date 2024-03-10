package jkproject.backend.board.service.heart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.backend.board.data.dto.heart.request.HeartCreateRequestDto;
import jkproject.backend.board.data.entity.heart.Heart;
import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.board.repository.heart.HeartRepository;
import jkproject.backend.board.repository.post.PostRepository;
import jkproject.backend.common.exception.ApplicationException;
import jkproject.backend.common.exception.enums.ErrorCode;
import jkproject.backend.user.data.dto.UserAuthenticationDto;
import jkproject.backend.user.data.entity.User;
import jkproject.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final HeartRepository heartRepository;

	public void createHeart(Long postId, HeartCreateRequestDto requestDto, UserAuthenticationDto userDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));
		if (userDto == null) {
			throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
		}
		User user = userRepository.findById(userDto.getUserId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		if (heartRepository.existsByUserAndPost(user, post)) {
			throw new ApplicationException(ErrorCode.INVALID_CREATE_HEART);
		}

		postRepository.updateHeartCount(post, requestDto.isNotHeart());
		Heart heart = requestDto.toEntity(user, post);
		post.addHeart(heart);
		heartRepository.save(heart);
	}

	// public void deleteHeart(Long postId, UserAuthenticationDto userDto) {
	// 	Post post = postRepository.findById(postId)
	// 		.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));
	//
	// 	User user = userRepository.findByLoginId(userDto.getLoginId())
	// 		.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
	//
	// 	Heart heart = heartRepository.findByUserAndPost(user, post)
	// 		.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_HEART_BY_USER_AND_POST));
	//
	// 	heartRepository.delete(heart);
	// }

	public long getHeartCount(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));

		long count = heartRepository.countByPost(post);

		return count;
	}
}
