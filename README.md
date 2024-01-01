# 🗒 커뮤니티 게시판 만들기

간단히 사용자들과 소통할 수 있는 게시판 서비스입니다. 

## 프로젝트 기능 및 설계
- 회원가입 기능
  - 사용자는 회원가입을 할 수 있다. 일반적으로 모든 사용자는 회원가입시 USER 권한 (일반 권한)을 지닌다. 
  - 회원가입시 아이디와 패스워드를 입력받으며, 이메일은 unique 해야한다. 

- 로그인 기능
  - 사용자는 로그인을 할 수 있다. 로그인시 회원가입때 사용한 아이디와 패스워드가 일치해야한다. 

- 게시글 작성 기능 
  - 로그인한 사용자는 권한에 관계 없이 글을 작성할 수 있다. 
  - 사용자는 게시글 제목(텍스트), 게시글 내용(텍스트)를 작성할 수 있다.

- 게시글 목록 조회 기능 
  - 로그인하지 않은 사용자를 포함한 모든 사용자는 게시글을 조회할 수 있다. 
  - 게시글은 최신순으로 기본 정렬되며, 좋아요가 많은순/적은순 으로도 정렬이 가능하다.
  - 게시글 목록 조회시 응답에는 게시글 제목과 작성일, 댓글 수의 정보가 필요하다.
  - 게시글은 종류가 많을수 있으므로 paging 처리를 한다. 1page 당 10

- 특정 게시글 조회 기능
  - 로그인하지 않은 사용자를 포함한 모든 사용자는 게시글을 조회할 수 있다. 
  - 게시글 제목, 게시글 내용, 작성자, 작성일이 조회된다.
  - 게시글을 읽거나 페이지를 방문할 때마다 해당 컨텐츠의 조회수가 증가
    
- 댓글 작성 기능
  - 로그인한 사용자는 권한에 관계 없이 댓글을 작성할 수 있다. 
  - 사용자는 댓글 내용(텍스트)를 작성할 수 있다.

- 게시물 검색
   - 로그인하지 않은 사용자를 포함한 모든 사용자는 게시글을 검색 할 수 있다.
   - 검색결과는 최신순으로 기본정렬된다.

 - 게시물 좋아요 기능
   - 사용자는 게시물에 좋아요를 할수있다.
   - 같은 사용자가 한 게시물에 중복해서 좋아요를 할 수 없어야 한다.
   - 사용자가 이미 한 좋아요를 다시 클릭하면 좋아요를 취소할 수 있도록 토글 기능을 제공한다.


## ERD 
![ERD](doc/img/%EC%BB%A4%EB%AE%A4%EB%8B%88%ED%8B%B0%20%EA%B2%8C%EC%8B%9C%ED%8C%90.png)

## Trouble Shooting
[go to the trouble shooting section](doc/TROUBLE_SHOOTING.md)

<div align=center><h1>📚 STACKS</h1></div>

<div align=center>

  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/redis-F80000?style=for-the-badge&logo=redis&logoColor=white"> 
  <img src="https://img.shields.io/badge/elasticsearch-F8DC75?style=for-the-badge&logo=elasticsearch&logoColor=white"> 
</div>
