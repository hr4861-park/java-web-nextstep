# 7장. DB를 활용해 데이터를 영구적으로 저장하기

- 자바 진영은 JDBC라는 표준을 통해 DB와의 통신을 담당하도록 지원.
- `java.sql` 패키지의 JDBC 소스코드를 열어보면 구현은 거의 없고, 인터페이스만 정의되어 있음.
- 데이터베이스를 만들어 서비스하는 회사가 구현체를 제공하도록 함.
- 서블릿 또한 인터페이스만 정의하고 서블릿 컨테이너를 제공하는 회사가 구현체를 제공하도록 함.

## 느낀점

- 저자가 테스트 코드를 제공해준 덕분에 편하게 리팩토링 가능!