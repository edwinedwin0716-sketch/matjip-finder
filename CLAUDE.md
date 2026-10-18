# 작업 규칙

- 사용자는 비전공자 간호학생이다. 설명은 쉬운 한국어로, 만들 수 있는 건 직접 만들어서 올린다.
- 프로그램마다 **기기별로 최적화한 버전**을 따로 만든다.
  - 안드로이드: WebView apk (`android/`, `sh android/build-apk.sh`). 지도·카카오·네이버 링크는 밖의 앱으로 넘긴다.
  - Windows 10/11: Edge 앱 창 실행기 exe (`windows/`, `sh windows/build.sh`, mono-mcs `-langversion:5`).
  - 아이폰·아이패드 / Mac: 깃허브 페이지 웹앱 (manifest + `sw.js`, 홈 화면·Dock에 추가).
- 웹앱 주소: https://edwinedwin0716-sketch.github.io/matjip-finder/ (main 브랜치 루트가 그대로 배포된다)
- 만든 설치 파일은 `download/`에 넣고 README의 "기기별로 받기" 표를 고친다.
- 다운로드 모음 페이지는 nurse_ai 저장소(`tools/download_page.html`)에도 탭으로 있다. 새 버전이면 거기 링크도 고친다.
- API 키, 실명, 개인정보는 저장소에 올리지 않는다. 서명 키(`release.keystore`)도 올리지 않는다.
