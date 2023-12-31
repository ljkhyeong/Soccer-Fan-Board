package jkproject.soccer.domain.entity.board.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jkproject.soccer.domain.entity.BaseTimeEntity;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "COMMENT_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

	//TODO PROTECTED 접근 제한자로 생성자를 만드는 이유
	//TODO IDENTITY 외의 GeneratedValue 전략
	//TODO PhoneNumber 등을 Embeded 타입으로 하면 장단점
	//TODO 지연로딩
	//TODO 지금 이 상황에서 다대일 양방향이어야 하나? 단방향으로 하면 안되나?

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentId;
	@Column(nullable = false)
	private String commenter;
	@Column(nullable = false)
	private String comment;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@Builder
	public Comment(String commenter, String comment, User user, Post post) {
		this.commenter = commenter;
		this.comment = comment;
		this.user = user;
		this.post = post;
	}

}
