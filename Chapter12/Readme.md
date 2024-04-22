# Chapter 12. 확장성 있는 DI 프레임워크로 개선

## @Injection

- 기존에는 생성자 주입만 가능했으나, Field, Setter 주입도 가능하도록 변경
- 리플렉션을 사용하여 런타임에 @Inject 어노테이션이 붙은 필드와 메소드를 스캔, 해당 타입의 객체를 주입해주는 형태
- 다만, Field injection, Setter injection 모두 현재 권장되지 않는 방식
  - 순환참조가 발생했음을 확인하기 어렵고
  - 테스트코드 작성이 Constructor injection 대비 어렵다.

## 객체지향

- 객체지향 설계의 핵심은 객체가 한 가지 역할과 책임을 갖도록 하는것
- 이후 객체간의 협업을 통해 동작하는 애플리케이션을 완성해 나간다.

## Etc

- web.xml 없이 개발하기
- @Component 지원
- 외부 라이브러리를 빈으로 등록
- @ComponentScan 지원

-> SpringBoot 에서 지원하는 기능들. Spring Framework로 시작해서 Springboot의 일부까지 구현
