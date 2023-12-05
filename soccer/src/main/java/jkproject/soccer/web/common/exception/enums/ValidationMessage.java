package jkproject.soccer.web.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ValidationMessage {

	CANNOT_BE_NULL("필수로 입력되어야 합니다."),
	UNMATCHED_LOGINID("아이디는 4자 이상, 15자 이하의 영문자 혹은 영문자와 숫자의 조합이어야 합니다.");
	// TODO User Validation에 들어갈 메세지

	private final String message;

}
