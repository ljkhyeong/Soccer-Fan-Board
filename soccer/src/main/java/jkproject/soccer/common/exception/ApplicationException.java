package jkproject.soccer.common.exception;

import jkproject.soccer.common.exception.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 작성자: 임정규
 * 작성일자: 2023-12-21
 * 비정상적인 요청에 대한 응답을 일괄적으로 처리하기 위한 클래스
 * 이 예외를 발생시키면 GlobalExceptionHandler에서 받아 예외를 처리한다
 */
@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

	private ErrorCode errorCode;

	@Override
	public String getMessage() {
		return errorCode.getMessage();
	}
}
