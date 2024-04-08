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

## 어노테이션 추가

위와 같이 리플렉션을 사용할 수 있도록 @Controller와 @RequestMapping 을 아래와 같이 구현한다.

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value() default "";
}
```

```java
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value() default "";
    RequestMethod method() default RequestMethod.GET;

    public static enum RequestMethod {
        GET, POST, PUT, DELETE
    }
}
```


## Controller Scanner 개발

@Controller 를 스캔하여 객체를 생성하는 클래스 추가


```java
public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(preInitiatedControllers);
    }

    Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        try {
            for (Class<?> clazz : preInitiatedControllers) {
                controllers.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }

        return controllers;
    }
}


```

## RequestMapping Handler 개발

ControllerScanner 를 실행시킬 RequestMapping Handler 를 작성.

```java


public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        Set<Method> methods = getRequestMappingMethods(controllers.keySet());
        for (Method method : methods) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            logger.debug("register handlerExecution : url is {}, method is {}", rm.value(), method);
            handlerExecutions.put(createHandlerKey(rm),
                    new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
        }

        logger.info("Initialized AnnotationHandlerMapping!");
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getRequestMappingMethods(Set<Class<?>> controlleers) {
        Set<Method> requestMappingMethods = Sets.newHashSet();
        for (Class<?> clazz : controlleers) {
            requestMappingMethods
                    .addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return requestMappingMethods;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        logger.debug("requestUri : {}, requestMethod : {}", requestUri, rm);
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
```

## 서블릿에 연결

서블릿의 init() 메소드에서 HandlerMapping 의 `initialize()`를 실행시켜, @Controller, @RequestMapping 을 사용한 클래스와 메소드를 스캔, 관련된 요청처리를 진행할 수 있게끔 한다.

```java
public class DispatcherServlet extends HttpServlet {
    private List<HandlerMapping> mappings = Lists.newArrayList();


    @Override
    public void init() throws ServletException {
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("next.controller");
        ahm.initialize();

        mappings.add(lhm);
        mappings.add(ahm);

        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
    }
}
```
