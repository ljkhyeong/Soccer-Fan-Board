package jkproject.backend.wiki.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import jkproject.backend.common.exception.ApplicationException;
import jkproject.backend.common.exception.enums.ErrorCode;
import jkproject.backend.common.validator.ValidationExceptionThrower;
import jkproject.backend.team.data.entity.Team;
import jkproject.backend.team.repository.TeamRepository;
import jkproject.backend.user.data.dto.UserAuthenticationDto;
import jkproject.backend.user.data.entity.User;
import jkproject.backend.user.repository.UserRepository;
import jkproject.backend.wiki.data.dto.request.DocVersionCreateRequestDto;
import jkproject.backend.wiki.data.dto.response.DocVersionDetailResponseDto;
import jkproject.backend.wiki.data.dto.response.DocVersionListResponseDto;
import jkproject.backend.wiki.data.entity.DocVersion;
import jkproject.backend.wiki.data.entity.WikiDoc;
import jkproject.backend.wiki.repository.DocVersionRepository;
import jkproject.backend.wiki.repository.WikiDocRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WikiService {

	private final WikiDocRepository wikiDocRepository;
	private final DocVersionRepository docVersionRepository;
	private final UserRepository userRepository;
	private final TeamRepository teamRepository;
	private final ValidationExceptionThrower validationExceptionThrower;

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

		validationExceptionThrower.ifErrorsThrow(errors, ErrorCode.INVALID_LOGIN);

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
