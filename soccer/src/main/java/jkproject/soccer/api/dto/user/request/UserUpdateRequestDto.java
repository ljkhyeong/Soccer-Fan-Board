package jkproject.soccer.api.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequestDto {

	@NotBlank
	private String password;
	@NotBlank
	private String nickname;
}
