package jkproject.soccer.api.controller.wiki;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.api.dto.wiki.request.WikiDocCreateRequestDto;
import jkproject.soccer.api.dto.wiki.response.WikiDocDetailResponseDto;
import jkproject.soccer.domain.service.wiki.WikiService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class WikiApiController {

	private final WikiService wikiService;

	@GetMapping("/{teamId}/wiki")
	public Response<WikiDocDetailResponseDto> readNewWikiDoc(@PathVariable Long teamId) {
		WikiDocDetailResponseDto wikiDocDto = wikiService.getNewWikiDocDetail(teamId);
		return Response.success(wikiDocDto);
	}

	@PostMapping("/{teamId}/wiki/{wikiDocId}")
	public Response<Void> createNewWikiDoc(@PathVariable Long teamId, @PathVariable Long wikiDocId,
		@RequestBody @Valid WikiDocCreateRequestDto requestDto,
		Errors errors, @AuthenticationPrincipal UserAuthenticationDto userDto) {
		wikiService.createNewWikiDoc(wikiDocId, requestDto, userDto, errors);
		return Response.success();
	}
}
