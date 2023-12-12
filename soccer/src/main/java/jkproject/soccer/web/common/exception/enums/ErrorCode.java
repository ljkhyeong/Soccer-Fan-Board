package jkproject.soccer.web.common.exception.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	NON_EXISTENT_USER_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 회원"),
	NON_EXISTENT_POST_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 게시물"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "유효하지 않은 비밀번호"),
	NOT_FOUND_TOKEN(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰");

	private final HttpStatus httpStatus;
	private final String message;
}