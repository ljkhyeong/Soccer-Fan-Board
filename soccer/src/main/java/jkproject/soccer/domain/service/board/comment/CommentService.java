package jkproject.soccer.domain.service.board.comment;

import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.api.dto.board.comment.request.CommentCreateRequestDto;
import jkproject.soccer.api.dto.board.comment.response.CommentListResponseDto;
import jkproject.soccer.domain.entity.board.comment.Comment;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.repository.board.comment.CommentRepository;
import jkproject.soccer.domain.repository.board.post.PostRepository;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public Page<CommentListResponseDto> readComments(Long postId, Pageable pageable) {
		Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
		return comments.map(CommentListResponseDto::from);
	}

	public void createComment(Long postId, CommentCreateRequestDto requestDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ApplicationContextException(ErrorCode.NON_EXISTENT_POST_ID.getMessage()));
		// TODO User도 넣어야함
		Comment comment = requestDto.toEntity(post);
		commentRepository.save(comment);
	}
}
