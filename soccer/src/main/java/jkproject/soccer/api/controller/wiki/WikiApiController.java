package jkproject.soccer.api.controller.wiki;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.api.dto.wiki.request.DocVersionCreateRequestDto;
import jkproject.soccer.api.dto.wiki.response.DocVersionDetailResponseDto;
import jkproject.soccer.api.dto.wiki.response.DocVersionListResponseDto;
import jkproject.soccer.domain.service.wiki.WikiService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WikiApiController {

	private final WikiService wikiService;

	@GetMapping("/{teamCode}/wiki")
	public Response<DocVersionDetailResponseDto> readNewDocVersion(@PathVariable String teamCode) {
		DocVersionDetailResponseDto wikiDocDto = wikiService.getNewDocVersion(teamCode);
		return Response.success(wikiDocDto);
	}

	@GetMapping("/{teamCode}/wiki/{wikiDocId}/list")
	public Response<Page<DocVersionListResponseDto>> lookupAllDocVersions(@PathVariable String teamCode,
		@PathVariable Long wikiDocId,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<DocVersionListResponseDto> docVersionDtoList = wikiService.lookUpAllDocVersion(wikiDocId, pageable);
		return Response.success(docVersionDtoList);
	}

	@PostMapping("/{teamCode}/wiki")
	public Response<Void> createNewDocVersion(@PathVariable String teamCode,
		@RequestBody @Valid DocVersionCreateRequestDto requestDto,
		Errors errors, @AuthenticationPrincipal UserAuthenticationDto userDto) {
		wikiService.createNewDocVersion(teamCode, requestDto, userDto, errors);
		return Response.success();
	}

	@GetMapping("/{teamCode}/wiki/{wikiDocId}")
	public Response<DocVersionDetailResponseDto> readDocVersion(@PathVariable String teamCode,
		@PathVariable Long wikiDocId, @RequestParam Integer version) {
		DocVersionDetailResponseDto wikiDocDto = wikiService.getDocVersion(wikiDocId, version);
		return Response.success(wikiDocDto);
	}
}
