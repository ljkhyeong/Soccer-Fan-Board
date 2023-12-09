package jkproject.soccer.web.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationMessage {

	CANNOT_BE_NULL(Messages.NOT_NULL),
	UNMATCHED_LOGINID(Messages.LOGIN_REQUIREMENTS);

	public static class Messages {
		public static final String NOT_NULL = "필수로 입력되어야 합니다.";
		public static final String LOGIN_REQUIREMENTS = "아이디는 4자 이상, 15자 이하의 영문자 혹은 영문자와 숫자의 조합이어야 합니다.";
	}

	private final String message;

}