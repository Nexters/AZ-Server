# AZ-Server
2020 넥스터즈 AZ 프로젝트 서버입니다.
- [안드로이드 다운로드](https://play.google.com/store/apps/details?id=com.az.youtugo&hl=ko)
- [아이폰 다운로드](https://apps.apple.com/kr/app/%EC%95%84%EC%9E%AC%ED%8A%B8/id1527266145)

## 📄 API 문서
- swagger 별도 정리

## 📂 프로젝트 구조(멀티 모듈)
- youtugo-application
  - api 모듈
- youtugo-domain
  - Domain, Repository, Service 레이어가 정의 되는 모델
- youtugo-core
  - 모든 모듈에서 사용되는 공통 모듈

## 💻 기술 스택
- java8, spring boot2.3, jpa, mysql, gitaction, AWS(S3, EC2, RDS, CodeDeploy)

## ⚒ CI/CD
- git action, aws 플랫폼을 이용한 CI/CD 적용

  
## 😽 git branch 전략
- [git-flow](https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html)
- master
- develop
- feature/{issue-number}

## ⚙ 환경 변수(intelij 기준)
<img width="690" alt="스크린샷 2020-07-19 오전 10 45 28" src="https://user-images.githubusercontent.com/50758600/87865186-0e05ae80-c9ad-11ea-9552-61f85e054f07.png">

secrets.MYSQL_URL
- 예시 : jdbc:mysql://localhost:3306/angelhack

secrets.MYSQL_USER
- 예시 : root

secrets.MYSQL_PW
- 예시 : 1111

secrets.JWT_EXPIRATION={JWT의 만료기간}
- 예시 : 6000000

secrets.JWT_SECRET={JWT에 사용되는 SECRET KEY }
- 예시 : abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz123456789

