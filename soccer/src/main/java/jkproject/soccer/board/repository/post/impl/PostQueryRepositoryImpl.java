package jkproject.soccer.board.repository.post.impl;

import static jkproject.soccer.board.data.entity.post.QPost.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jkproject.soccer.board.data.dto.post.request.SearchCondition;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.board.repository.post.PostQueryRepository;
import jkproject.soccer.team.data.entity.Team;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public void updateHeartCount(Post post1, boolean notClicked) {
		if (notClicked) {
			queryFactory.update(post)
				.set(post.heartCount, post.heartCount.add(1))
				.where(post.eq(post1))
				.execute();
		} else {
			queryFactory.update(post)
				.set(post.heartCount, post.heartCount.subtract(1))
				.where(post.eq(post1))
				.execute();
		}
	}

	@Override
	public Page<Post> findAllByTeam(Team team, SearchCondition condition, Pageable pageable) {

		BooleanExpression searchCondition = post.team.eq(team);
		String type = condition.getType();
		String keyword = condition.getKeyword();

		if (type != null && !type.isBlank()) {
			if (type.equals("title")) {
				searchCondition = searchCondition.and(post.title.contains(keyword));
			} else if (type.equals("writer")) {
				searchCondition = searchCondition.and(post.writer.eq(keyword));
			} else if (type.equals("content")) {
				searchCondition = searchCondition.and(post.content.contains(keyword));
			}
		}
		List<Post> posts = queryFactory.selectFrom(post)
			.where(searchCondition)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long count = queryFactory.select(post.count())
			.from(post)
			.where(searchCondition)
			.fetchOne();

		return new PageImpl<>(posts, pageable, count);
	}

}
