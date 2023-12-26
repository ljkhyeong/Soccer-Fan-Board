package jkproject.soccer.web.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationMessage {

	CANNOT_BE_NULL(Messages.NOT_NULL),
	UNMATCHED_LOGINID(Messages.LOGIN_REQUIREMENTS),
	EXISTS_LOGINID(Messages.EXISTS_LOGINID),
	EXISTS_NICKNAME(Messages.EXISTS_NICKNAME);

	public static class Messages {
		public static final String NOT_NULL = "필수로 입력되어야 합니다.";
		public static final String LOGIN_REQUIREMENTS = "아이디는 4자 이상, 15자 이하의 영문자 혹은 영문자와 숫자의 조합이어야 합니다.";
		public static final String EXISTS_LOGINID = "이미 존재하는 아이디입니다.";
		public static final String EXISTS_NICKNAME = "이미 존재하는 닉네임입니다.";
	}

	private final String message;

}