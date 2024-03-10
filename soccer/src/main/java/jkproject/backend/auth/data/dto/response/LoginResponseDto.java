package jkproject.backend.auth.data.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
	private String accessToken;
	private String refreshToken;
}
