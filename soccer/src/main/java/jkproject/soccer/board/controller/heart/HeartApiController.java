package jkproject.soccer.board.controller.heart;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jkproject.soccer.board.service.heart.HeartService;
import jkproject.soccer.common.data.dto.response.Response;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HeartApiController {

	private final HeartService heartService;

	@PostMapping("/{teamCode}/posts/{postId}/heart")
	public Response<Void> createHeart(@PathVariable String teamCode, @PathVariable Long postId,
		@AuthenticationPrincipal UserAuthenticationDto userDto) {
		heartService.createHeart(postId, userDto);
		return Response.success();
	}

	@DeleteMapping("/{teamCode}/posts/{postId}/heart")
	public Response<Void> deleteHeart(@PathVariable String teamCode, @PathVariable Long postId,
		@AuthenticationPrincipal UserAuthenticationDto userDto) {
		heartService.deleteHeart(postId, userDto);
		return Response.success();
	}

	// TODO 쿼리를 한 번 더 날려야 하므로 Post 내 필드로 관리하는 것이 더 나을 수 있다. (실시간 렌더링 vs 쿼리 수) 임시 메소드로 두자.
	@GetMapping("/{teamCode}/posts/{postId}/heart")
	public Response<Long> getHeartCount(@PathVariable String teamCode, @PathVariable Long postId) {
		long heartCount = heartService.getHeartCount(postId);
		return Response.success(heartCount);
	}
}
