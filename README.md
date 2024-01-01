# Soccer-Fan-Board
## 요구사항
1. 일반 사용자
- 회원가입 및 로그인:
  - 누구나 회원가입을 할 수 있어야 하며, 이메일과 비밀번호를 이용하여 로그인 할 수 있어야 한다.
  - 회원가입 시 필요한 정보는 이메일, 비밀번호, 사용자 이름 등이다.
- 위키 문서 열람:
  - 모든 사용자는 생성된 팀 위키 문서를 자유롭게 열람할 수 있어야 한다.
- 경기 일정 및 선수단 정보 열람:
  - 모든 사용자는 경기 일정 및 선수단 정보를 열람할 수 있어야 한다.

2. 등록된 회원
- 팀 위키문서 작성:
  - 로그인된 회원은 팀 위키문서를 작성할 수 있어야 한다. 팀 소개, 역사, 중요 이벤트 등의 내용을 포함할 수 있다.
  - 생성된 위키 문서는 수정하거나 삭제할 수 없다.
- 게시판 이용:
  - 로그인된 회원은 게시판에 글을 작성할 수 있다.
  - 로그인된 회원은 게시판의 글에 댓글을 달 수 있다.

3. 기술 요구사항
- 데이터베이스
  - 사용자 정보, 위키 문서, 게시판 글 및 댓글, 경기 일정 및 선수단 정보를 저장하기 위한 데이터베이스가 필요하다.
- 보안
  - 사용자 비밀번호는 암호화하여 저장해야 한다.
  - 로그인 시스템은 보안적으로 안전해야 하며, jwt 기반 인증을 사용한다.
- 인터페이스:
  - 사용자 친화적인 웹 인터페이스를 갖추어야 한다.
  - 모바일 및 데스크톱 환경에서 모두 잘 작동해야 한다.
- 확장성:
  - 향후 기능 추가나 변경을 고려한 유연한 설계가 필요하다.

4. 추가 고려사항
- 다국어 지원:
  - 다양한 사용자를 위해 다국어 지원을 고려할 수 있다.
---
> ## API

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
2. 댓글 컨트롤러 (CommentApiController)
- GET /api/v1/posts/{postId}/comments
  - 목적: 특정 게시물의 모든 댓글 조회
  - 매개변수: postId: 게시물 ID
  - 응답: Page<CommentListResponseDto>: 댓글 목록
- POST /api/v1/posts/{postId}/comment
  - 목적: 특정 게시물에 댓글 작성
  - 매개변수: postId: 게시물 ID
  - 요청 본문: CommentCreateRequestDto (댓글 내용 등)
  - 응답:
    - 성공 시: Response<Void>
    - 실패 시: 오류 메시지
3. 게시물 컨트롤러 (PostApiController)
- GET /api/v1/posts
  - 목적: 모든 게시물 조회
  - 응답: Page<PostListResponseDto>: 게시물 목록
- POST /api/v1/posts
  - 목적: 새 게시물 작성
  - 요청 본문: PostCreateRequestDto (게시물 제목, 내용 등)
  - 응답:
    - 성공 시: Response<Void>
    - 실패 시: 오류 메시지
- GET /api/v1/posts/{postId}
  - 목적: 특정 게시물 조회
  - 매개변수: postId: 게시물 ID
  - 응답: PostDetailResponseDto: 게시물 상세 정보
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

(예정)
- 공통
  - 문서 제목으로 문서 찾기 : Query wiki_doc_pk
- /api/v1/w/[문서이름]
  - 해당 문서의 최신 버전 읽기 : Query wiki_doc_pk
- /api/v1/edit/[문서이름]
  - 해당 문서의 최신 버전 읽기 : Query wiki_doc_pk
  - 새로운 문서 생성하기 : Mutation insert_wiki_doc
  - 문서의 새로운 버전 생성하기 : Mutation insert_doc_version
- /api/v1/history[문서이름]
  - 해당 문서의 모든 버전 읽기 : Query wiki_doc_pk

---
## ERD
(수정예정)
![배리어프리 board](https://github.com/ljkhyeong/Soccer-Fan-Board/assets/115821049/fc83c217-64fd-438c-9a86-74bfd6e77381)

---
