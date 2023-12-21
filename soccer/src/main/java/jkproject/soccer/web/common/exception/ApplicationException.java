package jkproject.soccer.web.common.exception;

import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

	private ErrorCode errorCode;

	@Override
	public String getMessage() {
		return errorCode.getMessage();
	}
}
