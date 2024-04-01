# Chapter10. 새로운 MVC 프레임워크 구현을 통한 점진적 개선


## 지난 MVC 프레임워크의 문제점

1. 새로운 컨트롤러가 추가될 떄마다 RequestMapping 클래스에 URL과 컨트롤러를 추가해야 함.
2. URL 만 매핑가능하고, `METHOD` 는 매핑이 안됨.

-> 즉 확장에 불리한 구조를 갖고있다.

이를 어노테이션(@Controller, @RequestMapping)기반의 프레임워크로 점진적 진화시킨다.


## 클래스 스캔

어노테이션 기반의 MVC프레임워크 구현을 위해서는 @Controller, @RequestMapping 이 설정된 클래스를 찾을 수 있어야 함. 

런타임에 클래스의 속성을 체크하기 위해서 리플렉션(Reflection)을 사용한다.

### 리플렉션

런타임에 클래스의 정보를 조회 및 접근하는 기술, 해당 클래스가 어떤 필드와 메소드를 갖고 있는지 런타임에 확인할 수 있음.

```java
reflections.getTypesAnnotatedWith(Controller.class)
```

위와 같이 지정하여 @Controller 가 붙어있는 모든 클래스 목록을 가져올 수 있음.
