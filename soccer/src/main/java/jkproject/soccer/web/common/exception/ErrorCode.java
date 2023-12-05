package jkproject.soccer.web.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	INVALID_STUDENT_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 회원");
	// TODO 에러코드와 에러 메시지

	private final HttpStatus httpStatus;
	private final String message;
}
