package jkproject.soccer.domain.service.board.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.api.dto.board.post.request.PostCreateRequestDto;
import jkproject.soccer.api.dto.board.post.response.PostDetailResponseDto;
import jkproject.soccer.api.dto.board.post.response.PostListResponseDto;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.team.Team;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.repository.board.post.PostRepository;
import jkproject.soccer.domain.repository.team.TeamRepository;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.common.exception.ApplicationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import jkproject.soccer.web.common.validator.ValidationResultHandler;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final ValidationResultHandler validationResultHandler;
	private final TeamRepository teamRepository;

	public Page<PostListResponseDto> lookupAllPosts(Long teamId, Pageable pageable) {
		Page<Post> posts = postRepository.findAllByTeamId(teamId, pageable);

		return posts.map(PostListResponseDto::from);
	}

	@Transactional
	public void createPost(Long teamId, PostCreateRequestDto requestDto,
		UserAuthenticationDto userDto, Errors errors) {

		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_CREATE_POST);
		User user = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		Team team = teamRepository.findById(teamId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_ID));
		// TODO 사용자 검증로직 분리하자.
		Post post = requestDto.toEntity(team, user);
		postRepository.save(post);
	}

	@Transactional
	public PostDetailResponseDto readPost(Long postId) {
		increasePostViewCount(postId);

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));

		return PostDetailResponseDto.from(post);
	}

	private void increasePostViewCount(Long postId) {
		postRepository.increaseViewCountByPostId(postId);
	}
}
