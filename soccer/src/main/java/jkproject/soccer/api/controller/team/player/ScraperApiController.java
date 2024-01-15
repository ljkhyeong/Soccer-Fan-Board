package jkproject.soccer.api.controller.team.player;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.web.common.crawler.service.AutoScrapService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScraperApiController {

	private final AutoScrapService autoScrapService;

	// EnableScheduling으로 이적시장 종료에만 돌아가도록 할 것이므로 임시컨트롤러로 사용
	@GetMapping("/scrap")
	public Response<Void> operateScrapper() {
		autoScrapService.scrapPlayersInfo();
		return Response.success();
	}
}
