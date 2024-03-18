#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.

1. `ContextLoaderListener` 의 contextinitialized 호출
2. jwp.sql 실행
3. `DispatcherServlet`의 init() 실행

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.

1. CharacterEncodingFilter, ResourceFilter의 `doFilter` 메소드 실행
2. `/` 으로의 요청이므로 `HomeController` 의 `execute`를 실행
3. `home.jsp` 로 HTML을 생성하여 응답.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.

* 기본적으로 서블릿 컨테이너는 서블릿을 1개만 생성하여 재사용한다.
* 해당 컨트롤러에 요청이 여러개 왔을 때, 필드의 값을 할당하는 과정에서 동시성 문제가 발생할 수 있기 때문에, 일부 필드를 스레드의 스택메모리에 할당하도록 변경한다.

### 개선 전

```java
public class ShowController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();
    private Question question;
    private List<Answer> answers;

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        question = questionDao.findById(questionId);
        answers = answerDao.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }
}

```

### 개선 후

```java
public class ShowController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }
}
```
