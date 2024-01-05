package jkproject.soccer.domain.entity.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jkproject.soccer.api.dto.user.request.UserUpdateRequestDto;
import jkproject.soccer.domain.entity.BaseTimeEntity;
import jkproject.soccer.domain.entity.board.comment.Comment;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.wiki.DocVersion;
import jkproject.soccer.domain.enums.user.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "login_id", unique = true, nullable = false)
	private String loginId;
	@Column(unique = true, nullable = false)
	private String password;
	@Column(nullable = false)
	private String nickname;
	private String email;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@OneToMany(mappedBy = "user")
	private List<Post> posts = new ArrayList<>();
	@OneToMany(mappedBy = "user")
	private List<Comment> comments = new ArrayList<>();
	@OneToMany(mappedBy = "user")
	private List<DocVersion> docs = new ArrayList<>();

	@Builder
	public User(String loginId, String password, String nickname, String email, String phoneNumber, UserRole role) {
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public void updateUserData(UserUpdateRequestDto requestDto) {
		nickname = requestDto.getNickname();
		password = requestDto.getPassword();
	}

	public void updatePassword(String password) {
		this.password = password;
	}
}
