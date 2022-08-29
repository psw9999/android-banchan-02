# 2팀 - 반찬 주문 앱

<img width="903" alt="image" src="https://user-images.githubusercontent.com/54823396/187197465-88537f23-c901-409f-9f1d-9fcbdb64d53e.png">

## 주요 기능
#### 홈 화면
- 기획전, 든든한 메인요리, 뜨끈한 국물요리, 정갈한 밑반찬 탭 존재, swipe gesture로도 전환 가능
- 각 탭에서 상품을 장바구니에 추가 가능
- 든든한 메인요리 탭에서는 목록을 나타내는 방식을 달리할 수 있음(Grid, Linear)
- 든든한 메인요리, 뜨끈한 국물요리, 정갈한 밑반찬 탭에서는 필터링 기능 존재
- 앱바 장바구니 아이콘에는 장바구니 갯수 표시
- 앱바 프로필에는 현재 배송중인지 여부가 뱃지로 표시

#### 상품 상세 화면
- 상품의 상세 정보 확인 가능
- 장바구니에 추가, 수량 변경이 가능함

#### 장바구니 화면
- 장바구니에 추가한 상품 목록 보여줌
- 수량을 표기하는 텍스트 클릭시 직접 수량 선택 가능
- 장바구니에 담긴 수량을 수정할 수 있고 아이템을 제거할 수 있음
- 선택한 상품의 금액 합계를 확인할 수 있음
- 최근 본 상품 목록 일부 제공

#### 최근 본 상품 목록 화면
- 모든 최근 본 상품 목록 확인할 수 있음
- 해당 화면에서도 상품을 장바구니에 추가하거나, 상세 화면으로 이동할 수 있음 

#### 주문 내역 화면
- 이전 주문 내역 목록을 확인할 수 있음 
- 배송 상태 확인 가능

#### 주문 상세 화면
- 주문 상세 확인 가능
- 주문한 상품 목록, 수량, 금액, 배송 상태 확인 가능 

## 기술 스택

항목 | 내용
-- | --
Architecture | AAC ViewModel, Clean Architecture
Database | Room
Dependency Injection | Hilt
Network | Retrofit2
Asynchronous Processing | Coroutine Flow
Image | Glide
collaboration tool | Gather, duckly
Etc | WorkManager, AlarmManager

## ERD
![image](https://user-images.githubusercontent.com/29484377/187195577-a700ea1b-e9fa-4e3e-b723-5038c9ffd0cd.png)

## 발표자료
- [발표자료 링크](https://drive.google.com/file/d/1ynRekkM5JVatiQIBFid2FaOl7IWy1At9/view)

## wiki
- [wiki 링크](https://github.com/woowa-techcamp-2022/android-banchan-02/wiki)

## 멤버
|[박승운](https://github.com/psw9999)|[박해민](https://github.com/Haemin-Park)|
|-------------------------------------|-------------------------------------| 
| <img src="https://avatars.githubusercontent.com/u/29484377?v=4" width="100px" /> |  <img src="https://avatars.githubusercontent.com/u/54823396?v=4" width="100px" /> |
