# 우리동네 맛·볼거리

거주지를 입력하면 주변 음식점과 볼거리를 모아서, 원하는 종류의 음식을 추천해 주는 웹 앱입니다.

## 📥 기기별로 받기
| 기기 | 받기 | 설치 |
|---|---|---|
| 📱 안드로이드 | [![안드로이드 앱 받기](https://img.shields.io/badge/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EC%95%B1%20%EB%B0%9B%EA%B8%B0-E63946?style=for-the-badge&logo=android&logoColor=white)](https://github.com/edwinedwin0716-sketch/matjip-finder/raw/main/download/Matjip.apk) | 받은 apk를 눌러 설치 ("출처를 알 수 없는 앱" 허용) |
| 💻 Windows 10/11 | [![Windows 받기](https://img.shields.io/badge/Windows%20%EB%B0%9B%EA%B8%B0-0078D4?style=for-the-badge&logo=windows&logoColor=white)](https://github.com/edwinedwin0716-sketch/matjip-finder/raw/main/download/Matjip-Windows-1.0.zip) | 압축 풀기 → `맛볼거리.exe` (파란 창은 추가 정보 → 실행) |
| 📱 아이폰 · 아이패드 | [![아이폰에서 열기](https://img.shields.io/badge/%EC%95%84%EC%9D%B4%ED%8F%B0%EC%97%90%EC%84%9C%20%EC%97%B4%EA%B8%B0-111111?style=for-the-badge&logo=apple&logoColor=white)](https://edwinedwin0716-sketch.github.io/matjip-finder/) | 사파리로 열기 → 공유(□↑) → **홈 화면에 추가** |
| 🍎 Mac | [![Mac에서 열기](https://img.shields.io/badge/Mac%EC%97%90%EC%84%9C%20%EC%97%B4%EA%B8%B0-333333?style=for-the-badge&logo=apple&logoColor=white)](https://edwinedwin0716-sketch.github.io/matjip-finder/) | 사파리로 열기 → 메뉴 **파일 → Dock에 추가** (macOS 14 이상) |

- 안드로이드 · Windows 앱은 위 웹앱을 앱 창으로 여는 방식이라 **새 기능이 자동으로 반영**돼요.
- 아이폰은 애플 정책상 앱 파일을 직접 설치할 수 없어서 홈 화면 웹앱으로 써요. 전체 화면으로 열리고 아이콘도 생겨요.

## 기능
- 주소 + 반경(500m~3km)으로 주변 음식점·볼거리 수집 (OpenStreetMap Nominatim / Overpass API)
- 음식 종류 12가지 자동 분류 (한식, 고기·구이, 중식, 일식, 양식, 아시안, 치킨, 패스트푸드, 분식, 카페·디저트, 술집·바, 기타)
- 거리 가중 랜덤 추천 + 가장 가까운 볼거리를 "식후 코스"로 제안
- 지도 표시 (Leaflet), 카카오맵·네이버지도 바로가기

## 실행
`index.html`을 정적 서버로 열면 됩니다. API 키가 필요 없습니다.

## 한계
- 평점·영업 여부는 제공하지 않습니다 (카카오/네이버 API 키 필요).
- 음식 종류는 OSM 태그와 상호명 키워드로 분류하므로 일부는 "기타"로 남습니다.

## 폴더 구조
- `index.html`, `manifest.webmanifest`, `sw.js`, `icons/` — 웹앱 (아이폰 · Mac · 모든 브라우저, 깃허브 페이지로 배포)
- `android/` — 안드로이드 앱 (`sh android/build-apk.sh`, Ubuntu `android-sdk-platform-23 aapt dalvik-exchange apksigner zipalign`)
- `windows/` — Windows 10/11 실행기 (`sh windows/build.sh`, mono-mcs)
- `download/` — 만들어진 설치 파일
