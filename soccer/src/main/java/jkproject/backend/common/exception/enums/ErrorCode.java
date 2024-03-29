package jkproject.backend.common.exception.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 작성자: 임정규
 * 작성일자: 2023-12-05
 * 에러 코드와 에러 메시지를 관리하기 위한 Enum 클래스
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

	ALREADY_EXISTENT_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
	NON_EXISTENT_TEAM_CODE(HttpStatus.BAD_REQUEST, "존재하지 않는 팀 코드"),
	NON_EXISTENT_TEAM_NAME(HttpStatus.BAD_REQUEST, "존재하지 않는 팀 이름"),
	NON_EXISTENT_USER_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 회원"),
	NON_EXISTENT_USER_BY_EMAIL(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일의 회원"),
	NON_EXISTENT_USER_BY_NICKNAME_AND_EMAIL(HttpStatus.BAD_REQUEST, "존재하지 않는 닉네임과 이메일의 회원"),
	NON_EXISTENT_POST_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 게시물"),
	NON_EXISTENT_COMMENT_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 댓글"),
	NON_EXISTENT_HEART_BY_USER_AND_POST(HttpStatus.BAD_REQUEST, "해당 글에 좋아요가 존재하지 않습니다."),
	NON_EXISTENT_WIKIDOC_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 위키문서"),
	NON_EXISTENT_WIKIDOC_VERSION(HttpStatus.BAD_REQUEST, "해당하는 위키에 어떤 버전의 문서도 존재하지 않습니다."),
	INVALID_LOGIN(HttpStatus.BAD_REQUEST, "유효하지 않은 로그인 시도"),
	INVALID_JOIN(HttpStatus.BAD_REQUEST, "유효하지 않은 회원가입 시도"),
	INVALID_SEARCH_USER(HttpStatus.BAD_REQUEST, "유효하지 않은 아이디 혹은 비밀번호 찾기"),
	INVALID_UPDATE_USER(HttpStatus.BAD_REQUEST, "유효하지 않은 회원정보 수정"),
	NOT_EQUALS_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 정확하게 입력해주세요"),
	NOT_EQUALS_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "새 비밀번호와 확인이 일치하지 않습니다."),
	INVALID_CREATE_WIKI(HttpStatus.BAD_REQUEST, "유효하지 않은 위키문서 작성"),
	INVALID_CREATE_POST(HttpStatus.BAD_REQUEST, "유효하지 않은 게시물 작성"),
	INVALID_UPDATE_POST(HttpStatus.BAD_REQUEST, "유효하지 않은 게시물 수정"),
	INVALID_REMOVE_POST(HttpStatus.BAD_REQUEST, "유효하지 않은 게시물 삭제"),
	ALREADY_REMOVED_COMMENT(HttpStatus.BAD_REQUEST, "이미 삭제된 댓글입니다."),
	INVALID_CREATE_COMMENT(HttpStatus.BAD_REQUEST, "유효하지 않은 댓글 작성"),
	INVALID_CREATE_HEART(HttpStatus.BAD_REQUEST, "이미 해당 글에 좋아요 혹은 싫어요를 누른 상태입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "유효하지 않은 비밀번호"),
	INVALID_CREATE_MAIL(HttpStatus.INTERNAL_SERVER_ERROR, "메일 생성 중 에러"),
	INVALID_SEND_MAIL(HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송 중 에러"),
	NOT_FOUND_TOKEN(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰"),
	AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "인증 중 오류 발생"),
	AUTHORIZATION_ERROR(HttpStatus.FORBIDDEN, "인가 중 오류 발생"),
	INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
	REMOVED_PARENT_COMMENT(HttpStatus.BAD_REQUEST, "부모 댓글이 삭제되어 대댓글을 작성할 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}