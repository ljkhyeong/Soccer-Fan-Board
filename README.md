# Soccer-Fan-Board

<img width="1435" alt="스크린샷 2024-01-16 오전 5 38 07" src="https://github.com/ljkhyeong/Soccer-Fan-Board/assets/115821049/1fa7a90d-7061-4e3d-a8a9-ee135601d994">
<details>
 <summary>추가 스샷</summary>
  <img width="1435" alt="스크린샷 2024-01-16 오전 5 37 03" src="https://github.com/ljkhyeong/Soccer-Fan-Board/assets/115821049/fa1e9004-30ef-4204-ba52-0fd1821231ba">
  <img width="1433" alt="스크린샷 2024-01-16 오전 5 31 09" src="https://github.com/ljkhyeong/Soccer-Fan-Board/assets/115821049/82fc5bfe-709b-41f3-9bea-15f0683ee178">
 <img width="1438" alt="스크린샷 2024-01-16 오전 5 43 04" src="https://github.com/ljkhyeong/Soccer-Fan-Board/assets/115821049/f422c1d6-a72c-497a-bf21-e00771bbdf42">
<img width="1438" alt="스크린샷 2024-01-19 오후 8 09 08" src="https://github.com/ljkhyeong/Soccer-Fan-Board/assets/115821049/9faf86bf-d979-47fa-a1a5-d3f4203dba2e">
<img width="1431" alt="스크린샷 2024-01-25 오후 10 43 31" src="https://github.com/ljkhyeong/Soccer-Fan-Board/assets/115821049/2f673ef8-6d5b-4ad8-b972-52eee1815a1b">
</details>


## 개요
본 프로젝트는 축구 팬들에게 관련 팀 정보(선수단 정보, 경기일정 등)들과 소통 커뮤니티(위키문서, 게시판)를 제공하기 위한 프로젝트다. (현재 epl만 지원)

## 기술 스택
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> 
<img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/springdatajpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
<img src="https://img.shields.io/badge/querydsl-003545?style=for-the-badge&logoColor=white">


## 요구사항
1. 모든 회원
- 회원가입 및 로그인:
  - 누구나 회원가입을 할 수 있어야 하며, 이메일과 비밀번호를 이용하여 로그인 할 수 있어야 한다.
  - 소셜계정을 연동해 회원가입을 할 수 있어야 한다.
  - 회원가입 시 필요한 정보는 이메일, 비밀번호, 사용자 이름 등이다.
- 회원 정보:
  - 누구나 비공개 처리된 정보를 제외한 특정 회원 관련 정보를 확인할 수 있어야 한다.
  - 누구나 특정 회원 혹은 비회원(IP)을 차단할 수 있어야 한다.
- 팀 선택:
  - 모든 사용자는 원하는 팀을 선택하여 관련 위키, 게시판, 팀 정보 등을 열람할 수 있어야 한다. 
- 위키 문서 열람:
  - 모든 사용자는 생성된 팀 위키 문서를 자유롭게 열람할 수 있어야 한다.
  - 생성된 위키 문서는 수정하거나 삭제할 수 없다.
  - 내용이 선정적이거나 불쾌함을 주거나 부적절하면 신고할 수 있어야 한다.
- 경기 일정 및 선수단 정보 열람:
  - 모든 사용자는 경기 일정 및 선수단 정보를 열람할 수 있어야 한다.
  - 특정 선수 정보를 클릭하면 공격 포인트, 개인수상 등 세부정보를 열람할 수 있어야 한다.
- 게시판 열람:
  - 모든 사용자는 차단된 사용자의 글을 제외한 모든 팀의 게시물 및 댓글을 자유롭게 열람할 수 있어야 한다.
  - 분류된 탭을 통해서 베스트/전체글 혹은 원하는 말머리의 글만 조회할 수 있어야 한다.
  - 검색기능을 이용하여 원하는 글만 조회할 수 있어야 한다.
  - 내용이 선정적이거나 불쾌함을 주거나 부적절하면 신고할 수 있어야 한다.

2. 등록된 회원
- 회원정보 수정:
  - 로그인된 회원은 회원정보를 수정할 수 있어야 한다.
  - 로그인된 회원은 회원정보의 공개여부를 수정할 수 있어야 한다.
- 팀 위키문서 작성:
  - 로그인된 회원은 팀 위키문서를 작성할 수 있어야 한다. 팀 소개, 역사, 중요 이벤트 등의 내용을 포함할 수 있어야 한다.
- 게시판 이용:
  - 로그인된 회원은 게시판에 글을 작성, 수정, 삭제할 수 있어야 한다.
  - 로그인된 회원은 게시판의 글에 댓글을 작성, 삭제할 수 있어야 한다.
  - 로그인된 회원은 게시판의 글에 좋아요나 싫어요를 누를 수 있어야 한다.
 
3. 등록되지 않은 회원
- 팀 위키문서 작성:
  - 비회원은 임시닉네임/IP주소 앞부분(ex: 127.0)으로 팀 위키문서를 작성할 수 있어야 한다. 팀 소개, 역사, 중요 이벤트 등의 내용을 포함할 수 있다.
- 게시판 이용:
  - 비회원은 게시판에 임시닉네임/임시비밀번호/IP주소 앞부분으로 글을 작성, 수정, 삭제할 수 있어야 한다.
  - 비회원은 게시판의 글에 임시닉네임/임시비밀번호/IP주소 앞부분으로 댓글을 작성, 삭제할 수 있어야 한다.

4. 기술 요구사항
- 데이터베이스
  - 사용자 정보, 위키 문서, 게시판 글 및 댓글, 경기 일정 및 선수단 정보를 저장하기 위한 데이터베이스가 필요하다.
- 보안
  - 사용자 비밀번호는 암호화하여 저장해야 한다.
  - 로그인 시스템은 보안적으로 안전해야 하며, jwt 기반 인증을 사용한다.
- 인터페이스:
  - 사용자 친화적인 웹 인터페이스를 갖추어야 한다.
- 확장성:
  - 향후 기능 추가나 변경을 고려한 유연한 설계가 필요하다.

4. 추가 고려사항
- 다국어 지원:
  - 다양한 사용자를 위해 다국어 지원을 고려할 수 있다.
- 타 리그 지원:
  - 다양한 사용자를 위해 다양한 국가 리그, 하부 리그, 국가대표 정보 및 공간 지원을 고려할 수 있다.
---
## API

<details>
 <summary>API 명세</summary>

1. 인증 컨트롤러 (AuthController)
- POST /api/v1/auth/login
  - 목적: 사용자 로그인
  - 요청 본문: LoginRequestDto (로그인 ID, 비밀번호 등)
  - 응답:
    - 성공 시: Response<Void> (성공 메시지)
    - 실패 시: 오류 메시지
- POST /api/v1/auth/refresh
  - 목적: 액세스 토큰 갱신
  - 요청: 쿠키에 포함된 리프레시 토큰
  - 응답:
    - 성공 시: Response<Void> (새로운 액세스 및 리프레시 토큰을 쿠키에 설정)
    - 실패 시: 오류 메시지
- DELETE /api/v1/auth/refresh
  - 목적: 리프레시 토큰 비활성화
  - 요청: 쿠키에 포함된 리프레시 토큰
  - 응답:
    - 성공 시: Response<Void> (토큰 제거)
    - 실패 시: 오류 메시지
   
2. 게시물 컨트롤러 (PostApiController)
- GET /api/v1/{teamCode}/posts
  - 목적: 모든 게시물 조회
  - 응답: Page<PostListResponseDto>: 게시물 목록
- POST /api/v1/{teamCode}/posts
  - 목적: 새 게시물 작성
  - 요청 본문: PostCreateRequestDto (게시물 제목, 내용 등)
  - 응답:
    - 성공 시: Response<Void>
    - 실패 시: 오류 메시지
- GET /api/v1/{teamCode}/posts/{postId}
  - 목적: 특정 게시물 조회
  - 매개변수: postId: 게시물 ID
  - 응답: PostDetailResponseDto: 게시물 상세 정보
  
3. 댓글 컨트롤러 (CommentApiController)
- GET /api/v1/{teamCode}/posts/{postId}/comments
  - 목적: 특정 게시물의 모든 댓글 조회
  - 매개변수: postId: 게시물 ID
  - 응답: Page<CommentListResponseDto>: 댓글 목록
- POST /api/v1/{teamCode}/posts/{postId}/comment
  - 목적: 특정 게시물에 댓글 작성
  - 매개변수: postId: 게시물 ID
  - 요청 본문: CommentCreateRequestDto (댓글 내용 등)
  - 응답:
    - 성공 시: Response<Void>
    - 실패 시: 오류 메시지

4. 사용자 컨트롤러 (UserApiController)
- POST /api/v1/user
  - 목적: 새 사용자 계정 생성
  - 요청 본문: UserCreateRequestDto (사용자 정보)
  - 응답:
    - 성공 시: Response<Void>
    - 실패 시: 오류 메시지
- PUT /api/v1/user
  - 목적: 사용자 정보 업데이트
  - 요청 본문: UserUpdateRequestDto (업데이트할 사용자 정보)
  - 응답:
    - 성공 시: Response<Void>
    - 실패 시: 오류 메시지
- DELETE /api/v1/user
  - 목적: 사용자 계정 삭제
  - 응답:
    - 성공 시: Response<Void>
    - 실패 시: 오류 메시지
    
4. 위키 컨트롤러 (WikiApiController)
- GET /api/v1/{teamCode}/wiki
  - 목적: 위키 최신문서 확인
  - 응답: DocVersionDetailResponseDto
- GET /api/v1/{teamCode}/wiki/{wikiDocId}
  - 목적: 해당 버전 위키문서 조회
  - 응답: DocVersionDetailResponseDto
- GET /api/v1/{teamCode}/wiki/{wikiDocId}/list
  - 목적: 모든 버전 위키문서 조회
  - 응답: Page<DocVersionListResponseDto>: 위키문서 버전 목록
- POST /api/v1/{teamCode}/wiki
  - 목적: 새 버전 위키문서 작성
  - 요청 본문: DocVersionCreateRequestDto (위키문서 제목, 내용)
  - 응답:
    - 성공 시: Response<Void>
    - 실패 시: 오류 메시지

5. 선수 컨트롤러 (PlayerApiController)
- GET /api/v1/{teamCode}/players
  - 목적: 선수단 확인
  - 응답: Page<PlayerListResponseDto>: 선수 목록

6. 팀 컨트롤러 (TeamApiController)
- GET /api/v1/{teamCode}
  - 목적: 팀 정보 확인
  - 응답: TeamResponseDto


</details>


---
