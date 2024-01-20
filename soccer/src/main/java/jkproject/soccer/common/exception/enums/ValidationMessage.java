package jkproject.soccer.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 작성자: 임정규
 * 작성일자: 2023-12-05
 * Validation 실패시 실패한 필드명과 실패 메시지를 담음
 */
@Getter
@AllArgsConstructor
public enum ValidationMessage {

	CANNOT_BE_NULL(Messages.NOT_NULL),
	UNMATCHED_LOGINID(Messages.LOGINID_REQUIREMENTS),
	EXISTS_LOGINID(Messages.EXISTS_LOGINID),
	EXISTS_NICKNAME(Messages.EXISTS_NICKNAME);

	public static class Messages {
		public static final String NOT_NULL = "필수로 입력되어야 합니다.";
		public static final String LOGINID_REQUIREMENTS = "아이디는 영문, 숫자를 포함한 6-20자리여야 합니다.";
		public static final String NICKNAME_REQUIREMENTS = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.";
		public static final String PASSWORD_REQUIREMENTS = "비밀번호는 공백을 제외하고 영문, 숫자, 특수문자를 포함한 8~16자리여야 합니다.";
		public static final String EMAIL_REQUIREMENTS = "잘못된 이메일 형식입니다.";
		public static final String PHONENUMBER_REQUIREMENTS = "잘못된 전화번호 형식입니다.";

		public static final String EXISTS_LOGINID = "이미 존재하는 아이디입니다.";
		public static final String EXISTS_NICKNAME = "이미 존재하는 닉네임입니다.";
	}

	private final String message;

}