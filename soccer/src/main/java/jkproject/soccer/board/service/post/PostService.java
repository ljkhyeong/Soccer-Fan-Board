package jkproject.soccer.board.service.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.board.data.dto.post.request.PostCreateRequestDto;
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

	@Transactional
	public void createPost(String teamCode, PostCreateRequestDto requestDto,
		UserAuthenticationDto userDto, Errors errors) {

		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_CREATE_POST);
		User user = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		Team team = teamRepository.findByCode(teamCode)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_CODE));
		// TODO 사용자 검증로직 분리하자.
		Post post = requestDto.toEntity(team, user);
		postRepository.save(post);
	}

	@Transactional
	public PostDetailResponseDto readPost(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));

		post.increaseViewCount();

		return PostDetailResponseDto.from(post);
	}

}
