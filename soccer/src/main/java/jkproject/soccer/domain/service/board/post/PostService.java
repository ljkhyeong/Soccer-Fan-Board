package jkproject.soccer.domain.service.board.post;

import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.api.dto.board.post.request.PostCreateRequestDto;
import jkproject.soccer.api.dto.board.post.response.PostDetailResponseDto;
import jkproject.soccer.api.dto.board.post.response.PostListResponseDto;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.repository.board.post.PostRepository;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public Page<PostListResponseDto> lookupAllPosts(Pageable pageable) {
		Page<Post> posts = postRepository.findAll(pageable);

		return posts.map(PostListResponseDto::from);
	}

	@Transactional
	public void createPost(PostCreateRequestDto requestDto, UserAuthenticationDto userDto) {
		//TODO User 넣어야함
		User user = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationContextException(ErrorCode.NON_EXISTENT_USER_ID.getMessage()));
		Post post = requestDto.toEntity(user);
		postRepository.save(post);
	}

	public PostDetailResponseDto readPost(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationContextException(ErrorCode.NON_EXISTENT_POST_ID.getMessage()));

		return PostDetailResponseDto.from(post);
	}
}
