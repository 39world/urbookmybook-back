## mainProject "책과 콩나무" Backend

함께 독서하고 추천하는 오프라인 도서 교환 서비스



## **Frontend github link**

[dhmgmhw/urbookmybook-front](https://github.com/dhmgmhw/urbookmybook-front)



## 팀원

- Frontend: 문형원, 김지하
- Backend: 강상구, 이정빈, 임다희, 천재승
- Design: 최지예



## 진행기간

- 2021.04.23(금) ~ 2021.05.27(목)



## 개발 도구 및 환경

주요 구현: Java&Spring Framework(Springboot, SpringSecurity, Spring Data JPA 등)

JWT 생성/활용: jjwt

코드 간소화: lombok

DB: AWS Amazon RDS  

RDB 엔진: MySQL

서버 호스팅: EC2, NGINX

클라우드 스토리지: S3



## ER Diagram

![https://github.com/39world/urbookmybook-back/blob/main/src/images/ERD.jpg](https://github.com/39world/urbookmybook-back/blob/main/src/images/ERD.jpg)



## 주효 구현 기능(APIs, DB 구축)

### 1. 로그인/회원가입

- JWT(Json Web Token) 생성/확인/인증처리 등의 기능 구현



![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled.png)





### 2. 동네 책장

- 동네 책장 게시글 전체 조회 기능

- 게시글 등록/수정/삭제 기능

- 책 검색 기능

- 댓글 등록/수정/삭제 기능

- 관심 있는 게시글 스크랩 기능

- 교환 완료 및 포인트 지급 기능

- 상대방의 서재 조회 기능 구현

  

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%201.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%201.png)

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%202.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%202.png)

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%203.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%203.png)

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%204.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%204.png)

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%205.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%205.png)





### 3. 마이페이지

- 프로필 조회 기능
- 내가 쓴 게시글 조회 기능
- 프로필 수정/삭제 기능
- 내가 쓴 댓글 조회 기능
- 내가 스크랩한 게시글 조회 기능 구현



![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%206.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%206.png)

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%207.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%207.png)

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%208.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%208.png)

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%209.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%209.png)





### 4. 채팅방/메시지

- 채팅방 생성 기능
- 채팅방 입장 기능
- 채팅방 메시지 발행 처리 기능 구현



![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%2010.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%2010.png)

![https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%2011.png](https://github.com/39world/urbookmybook-back/blob/main/src/images/Untitled%2011.png)

 




