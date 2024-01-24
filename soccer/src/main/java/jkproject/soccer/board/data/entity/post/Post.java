package jkproject.soccer.board.data.entity.post;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jkproject.soccer.board.data.dto.post.request.PostUpdateRequestDto;
import jkproject.soccer.board.data.entity.comment.Comment;
import jkproject.soccer.board.data.entity.heart.Heart;
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
	private String ipAddress;
	private Long viewCount = 0L;
	private Long heartCount = 0L;
	private Long notHeartCount = 0L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "user_id")
	private User user;
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Heart> hearts;
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comment> comments;

	@Builder
	public Post(String title, String content, String writer, String ipAddress, Team team, User user) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.ipAddress = ipAddress;
		this.team = team;
		this.user = user;
	}

	public void increaseViewCount() {
		this.viewCount++;
	}

	public void addHeart(Heart heart) {
		this.hearts.add(heart);
	}

	public void update(PostUpdateRequestDto requestDto, String ipAddress) {
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
		this.ipAddress = ipAddress;
	}
}


