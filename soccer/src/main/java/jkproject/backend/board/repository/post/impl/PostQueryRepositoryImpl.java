package jkproject.backend.board.repository.post.impl;

import static jkproject.backend.board.data.entity.post.QPost.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jkproject.backend.board.data.dto.post.request.SearchCondition;
import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.board.repository.post.PostQueryRepository;
import jkproject.backend.team.data.entity.Team;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public void updateHeartCount(Post post1, boolean notHeart) {
		if (!notHeart) {
			queryFactory.update(post)
				.set(post.heartCount, post.heartCount.add(1))
				.where(post.eq(post1))
				.execute();
		}

		if (notHeart) {
			queryFactory.update(post)
				.set(post.notHeartCount, post.notHeartCount.add(1))
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

		PathBuilder<Post> entityPath = new PathBuilder<>(Post.class, "post");
		OrderSpecifier[] orderSpecifiers = getOrderSpecifiers(pageable, entityPath);

		List<Post> posts = queryFactory.selectFrom(post)
			.where(searchCondition)
			.offset(pageable.getOffset())
			.orderBy(orderSpecifiers)
			.limit(pageable.getPageSize())
			.fetch();

		Long count = queryFactory.select(post.count())
			.from(post)
			.where(searchCondition)
			.fetchOne();

		return new PageImpl<>(posts, pageable, count);
	}

	@Override
	public Page<Post> findBestAllByTeam(Team team, SearchCondition condition, Pageable pageable) {
		BooleanExpression searchCondition = post.team.eq(team).and(post.heartCount.goe(1));
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

		PathBuilder<Post> entityPath = new PathBuilder<>(Post.class, "post");
		OrderSpecifier[] orderSpecifiers = getOrderSpecifiers(pageable, entityPath);

		List<Post> posts = queryFactory.selectFrom(post)
			.where(searchCondition)
			.offset(pageable.getOffset())
			.orderBy(orderSpecifiers)
			.limit(pageable.getPageSize())
			.fetch();

		Long count = queryFactory.select(post.count())
			.from(post)
			.where(searchCondition)
			.fetchOne();

		return new PageImpl<>(posts, pageable, count);
	}

	private OrderSpecifier[] getOrderSpecifiers(Pageable pageable, PathBuilder<?> entityPath) {
		return pageable.getSort().stream()
			.map(order -> {
				PathBuilder path = entityPath.get(order.getProperty());
				return new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, path);
			}).toArray(OrderSpecifier[]::new);
	}

}
