package jkproject.backend.board.service.comment;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jakarta.servlet.http.HttpServletRequest;
import jkproject.backend.board.data.dto.comment.request.CommentCreateRequestDto;
import jkproject.backend.board.data.dto.comment.request.CommentDeleteRequestDto;
import jkproject.backend.board.data.dto.comment.response.CommentListResponseDto;
import jkproject.backend.board.data.entity.comment.Comment;
import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.board.repository.comment.CommentRepository;
import jkproject.backend.board.repository.post.PostRepository;
import jkproject.backend.common.exception.ApplicationException;
import jkproject.backend.common.exception.enums.ErrorCode;
import jkproject.backend.common.validator.ValidationExceptionThrower;
import jkproject.backend.user.data.dto.UserAuthenticationDto;
import jkproject.backend.user.data.entity.User;
import jkproject.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final ValidationExceptionThrower validationExceptionThrower;
	private final PasswordEncoder passwordEncoder;

	public Page<CommentListResponseDto> readComments(Long postId, Pageable pageable) {
		Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);

		return comments.map(CommentListResponseDto::from);
	}

	@Transactional
	public void createComment(Long postId, CommentCreateRequestDto requestDto,
		UserAuthenticationDto userDto, Errors errors, HttpServletRequest request) {
		validationExceptionThrower.ifErrorsThrow(errors, ErrorCode.INVALID_CREATE_COMMENT);

		String clientIp = request.getRemoteAddr();
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));
		Long parentId = requestDto.getParentId();

		Comment comment = createEntity(parentId, requestDto, userDto, post, clientIp);

		if (userDto == null) {
			comment.savePassword(passwordEncoder.encode(requestDto.getPassword()));
		}

		commentRepository.save(comment);
	}

	@Transactional
	public void deleteComment(Long commentId, UserAuthenticationDto userDto, CommentDeleteRequestDto requestDto) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_COMMENT_ID));

		checkDeletePermission(comment, userDto, requestDto);

		if (comment.isRemoved()) {
			throw new ApplicationException(ErrorCode.ALREADY_REMOVED_COMMENT);
		}
		comment.remove();
	}

	private void checkDeletePermission(Comment comment, UserAuthenticationDto userDto,
		CommentDeleteRequestDto requestDto) {
		if (requestDto.isNonUserComment()) {
			if (requestDto.getPassword() == null ||
				!passwordEncoder.matches(requestDto.getPassword(), comment.getPassword())) {
				throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
			}
		}
		if (!requestDto.isNonUserComment()) {
			if (userDto == null || !Objects.equals(userDto.getNickname(), comment.getCommenter())) {
				throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
			}
		}
	}

	private Comment createEntity(Long parentId, CommentCreateRequestDto requestDto, UserAuthenticationDto userDto,
		Post post, String clientIp) {
		User user = null;
		Comment parent = null;

		if (userDto != null) {
			user = userRepository.findById(userDto.getUserId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		}

		if (parentId != null) {
			parent = commentRepository.findById(parentId)
				.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_COMMENT_ID));

			if (parent.isRemoved()) {
				throw new ApplicationException(ErrorCode.REMOVED_PARENT_COMMENT);
			}
		}

		return requestDto.toEntity(parent, user, post, clientIp);
	}

}
