package jkproject.soccer.domain.service.board.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.api.dto.board.comment.request.CommentCreateRequestDto;
import jkproject.soccer.api.dto.board.comment.response.CommentListResponseDto;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.domain.entity.board.comment.Comment;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.repository.board.comment.CommentRepository;
import jkproject.soccer.domain.repository.board.post.PostRepository;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.common.exception.ApplicationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import jkproject.soccer.web.common.validator.ValidationResultHandler;
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
		Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
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
		Comment comment = requestDto.toEntity(user, post);
		commentRepository.save(comment);
	}
}
