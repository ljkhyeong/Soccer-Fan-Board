package jkproject.soccer.board.service.post;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jakarta.servlet.http.HttpServletRequest;
import jkproject.soccer.board.data.dto.post.request.PostAuthorityRequestDto;
import jkproject.soccer.board.data.dto.post.request.PostCreateRequestDto;
import jkproject.soccer.board.data.dto.post.request.PostDeleteRequestDto;
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
	private final PasswordEncoder passwordEncoder;

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

		if (userDto == null) {
			post.savePassword(passwordEncoder.encode(requestDto.getPassword()));
		}

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
		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_UPDATE_POST);

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));
		if (requestDto.isNonUserPost()) {
			if (requestDto.getPassword() == null ||
				!passwordEncoder.matches(requestDto.getPassword(), post.getPassword())) {
				throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
			}
		}
		if (!requestDto.isNonUserPost()) {
			if (userDto == null || !Objects.equals(userDto.getNickname(), post.getWriter())) {
				throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
			}
		}
		String clientIp = request.getRemoteAddr();

		post.update(requestDto, clientIp);
	}

	// TODO 현재 코드는 수정 시 두번이나 post를 조회해야한다.(권한 체크 후 재요청) 개선할 수 있는 지 확인 필요
	@Transactional
	public void deletePost(Long postId, UserAuthenticationDto userDto, PostDeleteRequestDto requestDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));

		if (requestDto.isNonUserPost()) {
			if (requestDto.getPassword() == null ||
				!passwordEncoder.matches(requestDto.getPassword(), post.getPassword())) {
				throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
			}
		}
		if (!requestDto.isNonUserPost()) {
			if (userDto == null || !Objects.equals(userDto.getNickname(), post.getWriter())) {
				throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
			}
		}

		postRepository.delete(post);
	}

	public void checkAuthority(Long postId, UserAuthenticationDto userDto, PostAuthorityRequestDto requestDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));

		// getUser null 여부로 체크하면 해당 유저 삭제 시 판단이 어려워질 수 있으므로
		if (post.getWriter().contains("(")) {
			if (requestDto.getPassword() == null ||
				!passwordEncoder.matches(requestDto.getPassword(), post.getPassword())) {
				throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
			}
		} else {
			if (userDto == null || !Objects.equals(userDto.getNickname(), post.getWriter())) {
				throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
			}
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
