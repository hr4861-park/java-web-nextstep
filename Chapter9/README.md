#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.

1. `ContextLoaderListener` 의 contextinitialized 호출
2. jwp.sql 실행
3. `DispatcherServlet`의 init() 실행

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.

1. CharacterEncodingFilter, ResourceFilter의 `doFilter` 메소드 실행
2. `/` 으로의 요청이므로 `HomeController` 의 `execute`를 실행
3. `home.jsp` 로 HTML을 생성하여 응답.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
