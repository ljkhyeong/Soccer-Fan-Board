package jkproject.soccer.board.service.post;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jakarta.servlet.http.HttpServletRequest;
import jkproject.soccer.board.data.dto.post.request.PostCreateRequestDto;
import jkproject.soccer.board.data.dto.post.request.PostUpdateRequestDto;
import jkproject.soccer.board.data.dto.post.request.SearchCondition;
import jkproject.soccer.board.data.dto.post.response.PostDetailResponseDto;
import jkproject.soccer.board.data.dto.post.response.PostListResponseDto;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.board.repository.post.PostRepository;
import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
import jkproject.soccer.common.validator.ValidationResultHandler;
import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.team.repository.TeamRepository;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final ValidationResultHandler validationResultHandler;
	private final TeamRepository teamRepository;

	public Page<PostListResponseDto> lookupAllPosts(String teamCode, SearchCondition condition, Pageable pageable) {
		Team team = teamRepository.findByCode(teamCode)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_CODE));
		Page<Post> posts = postRepository.findAllByTeam(team, condition, pageable);

		return posts.map(PostListResponseDto::from);
	}

	public Page<PostListResponseDto> lookUpBestPosts(String teamCode, SearchCondition condition, Pageable pageable) {
		Team team = teamRepository.findByCode(teamCode)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_CODE));
		Page<Post> posts = postRepository.findBestAllByTeam(team, condition, pageable);

		return posts.map(PostListResponseDto::from);
	}

	@Transactional
	public void createPost(String teamCode, PostCreateRequestDto requestDto,
		UserAuthenticationDto userDto, Errors errors,
		HttpServletRequest request) {

		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_CREATE_POST);
		String clientIp = request.getRemoteAddr();
		Team team = teamRepository.findByCode(teamCode)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_CODE));
		Post post = createEntity(team, requestDto, userDto, clientIp);

		// TODO 사용자 검증로직 분리하자.
		postRepository.save(post);
	}

	@Transactional
	public PostDetailResponseDto readPost(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));

		post.increaseViewCount();

		return PostDetailResponseDto.from(post);
	}

	@Transactional
	public void updatePost(Long postId, UserAuthenticationDto userDto, PostUpdateRequestDto requestDto, Errors errors,
		HttpServletRequest request) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));
		String clientIp = request.getRemoteAddr();

		checkPermission(post, userDto);
		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_CREATE_POST);

		post.update(requestDto, clientIp);
	}

	@Transactional
	public void deletePost(Long postId, UserAuthenticationDto userDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));

		checkPermission(post, userDto);

		postRepository.delete(post);
	}

	private void checkPermission(Post post, UserAuthenticationDto userDto) {
		if (userDto == null || !Objects.equals(post.getWriter(), userDto.getNickname())) {
			throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
		}
	}

	private Post createEntity(Team team, PostCreateRequestDto requestDto, UserAuthenticationDto userDto,
		String clientIp) {
		User user = null;

		if (userDto != null) {
			user = userRepository.findByLoginId(userDto.getLoginId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		}

		return requestDto.toEntity(team, user, clientIp);
	}

}
