package jkproject.soccer.board.repository.heart.impl;

import static jkproject.soccer.board.data.entity.post.QPost.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.board.repository.heart.HeartQueryRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HeartQueryRepositoryImpl implements HeartQueryRepository {

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
}
