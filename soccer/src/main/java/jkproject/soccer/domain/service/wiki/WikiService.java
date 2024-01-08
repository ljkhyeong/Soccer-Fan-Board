package jkproject.soccer.domain.service.wiki;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.api.dto.user.UserAuthenticationDto;
import jkproject.soccer.api.dto.wiki.request.DocVersionCreateRequestDto;
import jkproject.soccer.api.dto.wiki.response.DocVersionDetailResponseDto;
import jkproject.soccer.api.dto.wiki.response.DocVersionListResponseDto;
import jkproject.soccer.domain.entity.team.Team;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WikiService {

	private final WikiDocRepository wikiDocRepository;
	private final DocVersionRepository docVersionRepository;
	private final UserRepository userRepository;
	private final TeamRepository teamRepository;
	private final ValidationResultHandler validationResultHandler;

	public DocVersionDetailResponseDto getNewDocVersion(String teamCode) {
		Team team = teamRepository.findByCode(teamCode)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_CODE));

		WikiDoc wikiDoc = wikiDocRepository.findByTeam(team)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_WIKIDOC_ID));

		DocVersion docVersion = docVersionRepository.findTopDocByWikiDoc(wikiDoc)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_WIKIDOC_VERSION));

		return DocVersionDetailResponseDto.from(wikiDoc, docVersion);
	}

	public Page<DocVersionListResponseDto> lookUpAllDocVersion(Long wikiDocId, Pageable pageable) {
		Page<DocVersion> docVersions = docVersionRepository.findAllByWikiDocId(wikiDocId, pageable);

		return docVersions.map(DocVersionListResponseDto::from);
	}

	public DocVersionDetailResponseDto getDocVersion(Long wikiDocId, Integer version) {

		WikiDoc wikiDoc = wikiDocRepository.findById(wikiDocId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_WIKIDOC_ID));

		DocVersion docVersion = docVersionRepository.findByWikiDocAndVersion(wikiDoc, version)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_WIKIDOC_VERSION));

		return DocVersionDetailResponseDto.from(wikiDoc, docVersion);
	}

	@Transactional
	public void createNewDocVersion(String teamCode, DocVersionCreateRequestDto requestDto,
		UserAuthenticationDto userDto, Errors errors) {

		validationResultHandler.ifErrorsThrow(errors, ErrorCode.INVALID_LOGIN);

		Team team = teamRepository.findByCode(teamCode)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_CODE));
		WikiDoc wikiDoc = wikiDocRepository.findByTeam(team)
			.orElseGet(() -> wikiDocRepository.save(requestDto.toWikiDocEntity(team)));
		User user = userRepository.findByLoginId(userDto.getLoginId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		Integer topVersion = docVersionRepository.findTopVersionByWikiDoc(wikiDoc)
			.orElse(0);

		DocVersion newDoc = requestDto.toDocVersionEntity(wikiDoc, topVersion + 1, user);
		docVersionRepository.save(newDoc);
	}
}
