package jkproject.soccer.wiki.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
import jkproject.soccer.common.validator.ValidationResultHandler;
import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.team.repository.TeamRepository;
import jkproject.soccer.user.data.dto.UserAuthenticationDto;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.user.repository.UserRepository;
import jkproject.soccer.wiki.data.dto.request.DocVersionCreateRequestDto;
import jkproject.soccer.wiki.data.dto.response.DocVersionDetailResponseDto;
import jkproject.soccer.wiki.data.dto.response.DocVersionListResponseDto;
import jkproject.soccer.wiki.data.entity.DocVersion;
import jkproject.soccer.wiki.data.entity.WikiDoc;
import jkproject.soccer.wiki.repository.DocVersionRepository;
import jkproject.soccer.wiki.repository.WikiDocRepository;
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
		User user = userRepository.findById(userDto.getUserId())
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_USER_ID));
		Integer topVersion = docVersionRepository.findTopVersionByWikiDoc(wikiDoc)
			.orElse(0);

		DocVersion newDoc = requestDto.toDocVersionEntity(wikiDoc, topVersion + 1, user);
		docVersionRepository.save(newDoc);
	}
}
