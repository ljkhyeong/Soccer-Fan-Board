package jkproject.backend.board.data.entity.post;

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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jkproject.backend.board.data.dto.post.request.PostUpdateRequestDto;
import jkproject.backend.board.data.entity.comment.Comment;
import jkproject.backend.board.data.entity.heart.Heart;
import jkproject.backend.common.data.entity.BaseTimeEntity;
import jkproject.backend.team.data.entity.Team;
import jkproject.backend.user.data.entity.User;
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
	private String password;
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

	@Transient
	private boolean isUpdated = false;

	@Builder
	public Post(String title, String content, String writer, String ipAddress, String password, Team team, User user) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.ipAddress = ipAddress;
		this.password = password;
		this.team = team;
		this.user = user;
	}

	public void increaseViewCount() {
		this.viewCount++;
		this.isUpdated = false;
	}

	public void addHeart(Heart heart) {
		this.hearts.add(heart);
		this.isUpdated = false;
	}

	public void update(PostUpdateRequestDto requestDto, String ipAddress) {
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
		this.ipAddress = ipAddress;
		this.isUpdated = true;
	}

	@PreUpdate
	protected void onPreUpdate() {
		if (this.isUpdated) {
			modifiedAtUpdate();
		}
	}

	public void savePassword(String password) {
		this.password = password;
	}
}


