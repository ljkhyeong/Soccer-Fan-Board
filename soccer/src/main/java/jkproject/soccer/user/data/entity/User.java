package jkproject.soccer.user.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jkproject.soccer.common.data.entity.BaseTimeEntity;
import jkproject.soccer.user.data.enums.UserRole;
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
	@Column(unique = true, nullable = false)
	private String nickname;
	@Column(nullable = false)
	private String email;
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Builder
	public User(String loginId, String password, String nickname, String email, String phoneNumber, UserRole role) {
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updatePassword(String password) {
		this.password = password;
	}
}
