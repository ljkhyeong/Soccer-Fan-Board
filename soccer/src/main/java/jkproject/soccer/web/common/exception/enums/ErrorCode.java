package jkproject.soccer.web.common.exception.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	NON_EXISTENT_TEAM_CODE(HttpStatus.BAD_REQUEST, "존재하지 않는 팀"),
	NON_EXISTENT_USER_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 회원"),
	NON_EXISTENT_POST_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 게시물"),
	NON_EXISTENT_WIKIDOC_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 위키문서"),
	NON_EXISTENT_WIKIDOC_VERSION(HttpStatus.BAD_REQUEST, "해당하는 위키에 어떤 버전의 문서도 존재하지 않습니다."),
	INVALID_LOGIN(HttpStatus.BAD_REQUEST, "유효하지 않은 로그인 시도"),
	INVALID_JOIN(HttpStatus.BAD_REQUEST, "유효하지 않은 회원가입 시도"),
	INVALID_CREATE_WIKI(HttpStatus.BAD_REQUEST, "유효하지 않은 위키문서 작성"),
	INVALID_CREATE_POST(HttpStatus.BAD_REQUEST, "유효하지 않은 게시물 작성"),
	INVALID_CREATE_COMMENT(HttpStatus.BAD_REQUEST, "유효하지 않은 댓글 작성"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "유효하지 않은 비밀번호"),
	NOT_FOUND_TOKEN(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰"),
	AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "인증 중 오류 발생"),
	AUTHORIZATION_ERROR(HttpStatus.FORBIDDEN, "인가 중 오류 발생");

	private final HttpStatus httpStatus;
	private final String message;
}