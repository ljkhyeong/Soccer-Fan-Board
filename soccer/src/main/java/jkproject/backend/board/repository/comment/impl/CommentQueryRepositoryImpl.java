package jkproject.backend.board.repository.comment.impl;

import static jkproject.backend.board.data.entity.comment.QComment.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jkproject.backend.board.data.entity.comment.Comment;
import jkproject.backend.board.repository.comment.CommentQueryRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Comment> findAllByPostId(Long postId, Pageable pageable) {
		// 댓글을 페이징하지않고 전부보내서 프론트에서 페이징하면 데이터 전송량이 많아지고, 댓글과 대댓글 따로 쿼리를 보내면 쿼리수가 많아진다.
		List<Comment> comments = queryFactory.selectFrom(comment1)
			.leftJoin(comment1.parent).fetchJoin()
			.where(comment1.post.postId.eq(postId))
			.orderBy(comment1.parent.commentId.coalesce(comment1.commentId).asc(),
				comment1.createdAt.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long count = queryFactory.select(comment1.count())
			.from(comment1)
			.where(comment1.post.postId.eq(postId))
			.fetchOne();

		return new PageImpl<>(comments, pageable, count);
	}
}
