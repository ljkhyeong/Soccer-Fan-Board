package jkproject.soccer.board.data.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jkproject.soccer.common.data.entity.BaseTimeEntity;
import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.user.data.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "POST_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	@Column(nullable = false)
	private String writer;
	private Long viewCount = 0L;
	private Long heartCount = 0L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Post(String title, String content, String writer, Team team, User user) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.team = team;
		this.user = user;
	}

	public void increaseViewCount() {
		this.viewCount++;
	}
}


