package jkproject.soccer.board.service.comment;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.board.data.dto.comment.request.CommentCreateRequestDto;
import jkproject.soccer.board.data.dto.comment.response.CommentListResponseDto;
import jkproject.soccer.board.data.entity.comment.Comment;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.board.repository.comment.CommentRepository;
import jkproject.soccer.board.repository.post.PostRepository;
import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
import jkproject.soccer.common.validator.ValidationResultHandler;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final ValidationResultHandler validationResultHandler;

	public Page<CommentListResponseDto> readComments(Long postId, Pageable pageable) {
		Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);

		return comments.map(CommentListResponseDto::from);
	}

	@Transactional
	public void createComment(Long postId, CommentCreateRequestDto requestDto,
		UserAuthenticationDto userDto, Errors errors) {
		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_CREATE_COMMENT);

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_POST_ID));
		User user = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));

		Long parentId = requestDto.getParentId();
		Comment comment;

		if (parentId == null) {
			comment = requestDto.toEntity(null, user, post);
		} else {
			Comment parent = commentRepository.findById(parentId)
				.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_COMMENT_ID));
			comment = requestDto.toEntity(parent, user, post);
		}

		commentRepository.save(comment);
	}

	@Transactional
	public void deleteComment(Long commentId, UserAuthenticationDto userDto) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_COMMENT_ID));

		checkPermission(comment, userDto);

		comment.remove();
	}

	private void checkPermission(Comment comment, UserAuthenticationDto userDto) {
		if (userDto == null || !Objects.equals(comment.getCommenter(), userDto.getNickname())) {
			throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
		}
	}

}
