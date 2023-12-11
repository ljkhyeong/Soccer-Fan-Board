package jkproject.soccer.web.auth.config.jwt;

import org.springframework.http.ResponseCookie;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
	ACCESS("accessToken", 1000L * 60 * 30, false),
	REFRESH("refreshToken", 1000L * 60 * 30, true);

	// TODO XSS공격에 취약할 수 있으므로 HttpOnly옵션을 둘다 키도록 수정 요망
	private final String name;
	private final long validTimeMilli;
	private final boolean isHttpOnly;

	private long getValidTimeSec() {
		return validTimeMilli / 1000;
	}

	public ResponseCookie createCookieFrom(String token) {
		return createCookieFrom(token, getValidTimeSec());
	}

	public ResponseCookie removeCookie() {
		return createCookieFrom("", 0);
	}

	public ResponseCookie createCookieFrom(String token, long validTimeSec) {
		return ResponseCookie.from(name, token)
			.httpOnly(isHttpOnly)
			.secure(true)
			.maxAge(validTimeSec)
			.path("/")
			.build();
	}

}
