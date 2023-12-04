package jkproject.soccer.domain.service.board.post;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.domain.repository.board.post.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

}
