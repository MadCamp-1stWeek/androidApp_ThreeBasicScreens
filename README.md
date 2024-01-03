# SNAP

MAD CAMP Week 1 3분반 안희웅, 우원정 팀

- 연락처를 연동하여 검색과 추가가 가능합니다. 상세 정보창으로 들어가 전화 걸기, 메세지 전송, 연락처 수정 및 삭제가 가능합니다.
- 갤러리를 연동하여 카메라 촬영, 즐겨찾기 모아보기가 가능합니다. 사진 확대 보기 화면에서 사진 이름 확인, 즐겨찾기 추가, 삭제, 사진 편집앱으로 전송하여 사진 편집 및 저장, 사진 삭제, swipe를 통한 이웃한 사진 보기가 가능합니다.
- Snake game play가 가능합니다. Best score이 저장되어 연락처 프로필에 등록됩니다. 게임 화면에서 스크린샷 촬영 후 갤러리에 저장이 가능합니다.



## 1.0 개발 팀원
- 안희웅 - 고려대학교 컴퓨터학과 19학번
- 우원정 - KAIST 전산학부 21학분


## 2.0 개발 환경
- Language: Kotlin, xml
- OS: Android

        minSdk 21
        targetSdk 33

  - IDE: Android Studio
  - Target Device: Galaxy S10e


## 3.0 어플리케이션 소개
### 3.1 MainActivity

#### 3.1.1 주요 기능
- Logo가 생성되는 splash 화면이 구현됩니다
<p align = "center">
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/dc29fe34-7808-4226-b37c-fae9d5566693"  width="300"/>
</p>

- Contact 탭이 첫 화면으로 나타납니다
- 아래쪽 navigation bar를 통해 다른 탭으로 이동할 수 있습니다

#### 3.1.2 기술 설명
기술 설명
- GLIDE를 이용해 .gif파일을 첨부 하였습니다. 
- Handler를 이용해 지정된 시간 후에 MainActivity를 실행시키도록 하였습니다. 
- Contact fragment를  intent-filter에서 MAIN으로 설정해 첫 화면으로 나오게 하였습니다.
- navController와 appBarConfiguration으로 바텀 네비케이션 바를 동작하게 하였습니다.

### 3.2 탭 1: 연락처
#### 3.2.1 연락처 목록
<p align = "center">
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/dbf23166-d340-4b3d-abff-3d3aab5e5af1"  width="300"/>
</p>


##### 3.2.1.1 주요 기능
- 핸드폰의 연락처와 연동되어 확인, 수정, 삭제가 모두 가능합니다다
- 이름 순으로 정렬됩니다
- 프로필 사진, 이름, 전화번호를 확인할 수 있습니다
- 프로필 사진이 등록되지 않은 경우, 기본 프로필 사진이 적용됩니다
- 검색 바를 눌러 이름을 통해 연락처를 검색할 수 있습니다
- 검색 바 우측 + 버튼을 통해 연락처를 추가할 수 있습니다
- 연락처를 클릭하여 상세 페이지로 이동할 수 있습니다
- 연락처 우측의 게임 스코어를 눌러 메세지를 보내 게임 스크린샷을 자랑할 수 있습니다

##### 3.2.1.2 기술 설명
기술 설명
- 안드로이드 연락처 읽기/쓰기 권한을 획득해 휴대폰 연락처와 동기화 합니다.
- ContactsContract.CommonDataKinds.Phone을 통해 연락처 데이터를 가져옵니다.
- contenResolver.query를 사용해 불러올 데이터와 따로 구현한 search, sort 기능을 첨부하여 연락처 정보를 불러옵니다. 
- 구현한 contactAdd함수를 통해 연락처를 추가할 수 있습니다.(URI, 사진, 이름, 전화번호)
- 프로필 사진 추가는 Tab2에서 구현한 휴대폰 갤러리 연동을 통해 휴대폰에서 이미지를 불러와 저장할 수 있도록 했습니다. 
- 게임 스코어를 누를 시, 제일 최근에 스크린 샷한 사진 정보를 받아와 해당 연락처에 스크린 샷을 불러와 보낼 수 있도록 하였습니다.

#### 3.2.2 연락처 상세 페이지
<p align = "center">
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/a5d8c336-dd96-4813-b069-204256826371"  width="300"/>
</p>

##### 3.2.2.1 주요 기능
- 이름과 전화번호, 프로필 사진을 확인할 수 있습니다
- 전화 버튼을 클릭하여 전화를 걸 수 있습니다
- 메세지 버튼을 클릭하여 메세지를 보낼 수 있습니다
- 연필 버튼을 클릭하여 연락처를 수정할 수 있습니다
- 휴지통 버튼을 클릭하여 연락처를 삭제할 수 있습니다
- 삭제를 완료하면 연락처가 삭제된 연락처 목록으로 돌아갑니다

##### 3.2.2.2 기술 설명
기술 설명 
- Intent와 broadcasting을 통해 fragment, activity간 데이터 전달을 했습니다.
- 전화번호를 intent를 통해 전달해 휴대폰 기본 전화 앱에 전달하도록 했습니다.
- 전화번호를 intent를 통해 전달해 휴대폰 기본 메세지 앱에 전달하도록 했습니다. 
- 수정은 추가와 삭제로 구현하였습니다.
- contactDelete함수를 통해 이름과, 전화번호로 필터링하여 해당 연락처를 삭제합니다. 

#### 3.2.3 연락처 수정 페이지
<p align = "center">
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/14b03f07-1280-406d-8ae2-812fe8aa9b05"  width="300"/>
</p>


##### 3.2.3.1 주요 기능
- 프로필 사진을 눌러 갤러리의 새로운 사진 또는 카메라로 새로 찍은 사진으로 변경할 수 있습니다
- 이름을 눌러 연락처의 이름을 편집할 수 있습니다
- 번호를 눌러 연락처의 번호를 편집할 수 있습니다
- 변경 버튼을 눌러 편집을 완료할 수 있습니다
- 편집을 완료하면 편집된 연락처 목록 화면으로 돌아갑니다

##### 3.2.3.2 기술 설명
기술 설명
- 연락처 상세페이지에서 갤러리를 띄운 tab2와 연결해 갤러리에 접근해 새로운 사진을 불러올 수 있도록 했습니다.
- broadcasting과 intent를 써서 데이터 전달을 하고, fragment와 activity간 작업 수행 완료 등의 정보를 전달해 다른 화면을 띄운 화면이 종료될 수 있도록 했습니다. 

#### 3.2.4 연락처 추가 페이지
<p align = "center">
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/23fc71e9-532f-4990-bf3f-d2af15ba000e"  width="300"/>
</p>

##### 3.2.4.1 주요 기능
- 프로필 사진을 눌러 갤러리의 사진 또는 카메라로 새로 찍은 사진을 선택할 수 있습니다
- 이름을 눌러 새로운 이름을 입력할 수 있습니다
- 전화번호를 눌러 새로운 전화번호를 입력할 수 있습니다
- 완료 버튼을 눌러 새로운 전화번호를 저장할 수 있습니다
- 저장을 완료하면 연락처가 추가된 연락처 목록 화면으로 돌아갑니다

##### 3.2.4.2 기술 설명
기술설명
- 3.2.3.2 와 3.2.1.2 참고. 

### 3.3 탭 2: 갤러리

#### 3.3.1. 갤러리
<p align = "center">
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/5d0e988f-0008-47c2-9277-d903230ae5c2"  width="300"/>
</p>

##### 3.3.1.1 주요 기능
- 휴대폰의 갤러리와 연동되어 추가, 삭제, 편집이 모두 가능합니다
- 갤러리 사진들을 작은 화면으로 확인할 수 있습니다
- 우측 상단의 + 버튼을 눌러 카메라로 이동할 수 있습니다
- 카메라 촬영이 완료되면 해당 사진이 추가된 갤러리 목록 화면으로 돌아갑니다
- 좌측 상단의 빈 하트 버튼을 눌러 즐겨찾기 필터링을 활성화 할 수 있습니다
- 좌측 상단의 채워진 하트 버튼을 눌러 즐겨찾기 필터링을 비활성화 할 수 있습니다
- 사진을 클릭하여 상세 페이지로 이동할 수 있습니다

##### 3.3.1.2 기술 설명
- Grid recycler view를 통해 갤러리 목록을 구현하였습니다
- Cursor를 이용해 핸드폰의 갤러리와 연동합니다
- Intent를 이용해 카메라 촬영 기능을 구현하였습니다
- Bitmap을 전송해 timestamp를 활용한 이름과 uri로 갤러리에 저장하였습니다
- 즐겨찾기 리스트를 sqlite로 즐겨찾기 한 이미지들의 uri를 저장해 즐겨찾기 모아보기를 구현하였습니다
- 각 이미지 홀더에 클릭 리스너를 설정해 새로운 intent로 넘어갈 수 있도록 하였습니다

#### 3.3.2 이미지 상세 페이지
<p align = "center">
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/f4ce1156-642d-4622-a2c0-d0977674cf20"  width="300"/>
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/b375ca6c-672f-478b-8ebd-92dfbcebe65f"  width="300"/>
</p>

##### 3.3.2.1 주요 기능
- 확대된 이미지와 이미지의 이름을 확인할 수 있습니다
- 이미지를 클릭하여 전체 화면 모드로 들어갈 수 있습니다
- 즐겨찾기 여부를 하트 아이콘 형태 차이로 확인할 수 있습니다
- 연필 버튼을 클릭하여 이미지 편집 앱으로 이동, 편집 및 저장할 수 있습니다
- 휴지통 버튼을 클릭하여 이미지를 삭제할 수 있습니다
- Swipe를 통해 이웃한 사진으로 이동할 수 있습니다
- 이미지 삭제를 완료하면 이미지가 삭제된 갤러리로 돌아갑니다

##### 3.3.2.2 기술 설명
- Intent에 저장한 정보들을 받아 새로운 액티비티에 사진을 띄웁니다
- onSwipeTouchListener를 이용해 swipe 기능, touch 시 전체 화면 모드 기능을 구현했습니다
- 즐겨찾기 버튼을 이용하여 database에서 리스트를 편집할 수 있도록 하였습니다
- Intent를 이용해 사진 편집 기능을 구현하였습니다
- Uri로 저장된 파일을 삭제하는 방식으로 삭제 기능을 구현하였습니다

### 3.4 탭 3: SNAKE GAME

#### 3.4.1. SNAKE GAME
<p align = "center">
  <img src="https://github.com/MadCamp-1stWeek/androidApp_ThreeBasicScreens/assets/112631065/852aed3d-0a16-4f40-8232-8c5eb0f2a015"  width="300"/>
</p>


##### 3.4.1.1 주요 기능
- Start game 버튼을 눌러 Snake game을 할 수 있습니다
- 화면 swipe를 통해 뱀의 경로를 변경할 수 있습니다
- 뱀의 먹이 색이 랜덤으로 결정되며 색 그대로 뱀의 꼬리에 붙습니다. 
- 최고 기록을 확인할 수 있습니다
- 연락처에 최고 기록이 연동됩니다
- 게임 화면을 캡쳐하여 갤러리에 저장할 수 있습니다
- 가장 최근 캡쳐한 게임 화면이 기록되어, 연락처에서 자랑할 때 활용됩니다
- Start again 버튼을 눌러 새로운 게임을 시작할 수 있습니다
- 게임 화면 상단에서 현재 스코어를 확인할 수 있습니다

##### 3.4.1.2 기술 설명
- canvas를 이용하여 게임 화면을 구현하였습니다
- shared preferences를 이용하여 최고 기록을 저장했습니다
- 탭 2와 연동하여 캡쳐 기능을 구현하였습니다
- OnSwipeTouchListener를 이용하여 조작 방법을 구현하였습니다
- bitmap을 사용해 스크린 샷을 저장하였습니다.
- canvas를 다시 그려 snake게임 스크린 샷을 찍었습니다.
- x,y 좌표를 조정해 bitmap을 합쳤습니다.
- shared preferences를 이용해 제일 최근에 찍은 스크린 샷의 uri를 저장했습니다.
- 탭 1에서 shared preference에 있는 파일을 불러오도록 했습니다.
- colorArray를 따로 사용해 먹이의 색을 기억하고 적용했습니다. 
