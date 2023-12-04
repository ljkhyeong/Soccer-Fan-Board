package jkproject.soccer.api.dto.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

	private String resultCode;
	private T result;

	public static Response<Void> error(String errorCode) {
		return new Response<>(errorCode, null);
	}

	public static <T> Response<T> error(String errorCode, T result) {
		return new Response<>(errorCode, result);
	}

	public static Response<Void> success() {
		return new Response<>("SUCCESS", null);
	}

	public static <T> Response<T> success(T result) {
		return new Response<>("SUCCESS", result);
	}
	
}
