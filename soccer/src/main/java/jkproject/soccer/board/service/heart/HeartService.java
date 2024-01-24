package jkproject.soccer.board.service.heart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.board.data.dto.heart.request.HeartCreateRequestDto;
import jkproject.soccer.board.data.entity.heart.Heart;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.board.repository.heart.HeartRepository;
import jkproject.soccer.board.repository.post.PostRepository;
import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.user.repository.UserRepository;
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
		User user = userRepository.findByLoginId(userDto.getLoginId())
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
