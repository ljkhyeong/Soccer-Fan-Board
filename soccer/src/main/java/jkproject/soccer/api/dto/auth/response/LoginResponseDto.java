package jkproject.soccer.api.dto.auth.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
	private String accessToken;
	private String refreshToken;
}
