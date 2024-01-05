package jkproject.soccer.domain.service.wiki;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.api.dto.wiki.request.WikiDocCreateRequestDto;
import jkproject.soccer.api.dto.wiki.response.WikiDocDetailResponseDto;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.entity.wiki.DocVersion;
import jkproject.soccer.domain.entity.wiki.WikiDoc;
import jkproject.soccer.domain.repository.team.TeamRepository;
import jkproject.soccer.domain.repository.user.UserRepository;
import jkproject.soccer.domain.repository.wiki.DocVersionRepository;
import jkproject.soccer.domain.repository.wiki.WikiDocRepository;
import jkproject.soccer.web.common.exception.ApplicationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import jkproject.soccer.web.common.validator.ValidationResultHandler;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WikiService {

	private final WikiDocRepository wikiDocRepository;
	private final DocVersionRepository docVersionRepository;
	private final TeamRepository teamRepository;
	private final UserRepository userRepository;
	private final ValidationResultHandler validationResultHandler;

	@Transactional(readOnly = true)
	public WikiDocDetailResponseDto getNewWikiDocDetail(Long teamId) {
		WikiDoc wikiDoc = wikiDocRepository.findByTeamId(teamId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_WIKIDOC_ID));

		DocVersion docVersion = docVersionRepository.findTopDocByWikiDoc(wikiDoc)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_WIKIDOC_VERSION));

		return WikiDocDetailResponseDto.from(wikiDoc, docVersion);
	}

	public void createNewWikiDoc(Long wikiDocId, WikiDocCreateRequestDto requestDto,
		UserAuthenticationDto userDto, Errors errors) {

		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_LOGIN);

		WikiDoc wikiDoc = wikiDocRepository.findById(wikiDocId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_WIKIDOC_ID));
		User user = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		Integer topVersion = docVersionRepository.findTopVersionByWikiDoc(wikiDoc)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_WIKIDOC_VERSION));

		DocVersion newDoc = requestDto.toEntity(wikiDoc, topVersion, user);
		docVersionRepository.save(newDoc);
	}
}
