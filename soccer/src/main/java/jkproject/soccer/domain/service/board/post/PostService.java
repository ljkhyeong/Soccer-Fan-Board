package jkproject.soccer.domain.service.board.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.api.dto.board.post.response.PostListResponseDto;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.repository.board.post.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Page<PostListResponseDto> lookupAllPosts(Pageable pageable) {
		Page<Post> posts = postRepository.findAll(pageable);

		return posts.map(PostListResponseDto::from);
	}
}
