# Chapter 8. AJAX를 활용해 새로고침 없이 데이터 갱신하기.

## 웹 브라우저가 HTML을 처리하는 과정

1. 서버로부터 HTML을 수신.
2. 라인 단위로 읽어내려가면서 서버에 재요청이 필요한 부분(CSS, Javascript, image)을 찾아 다시 서버에 요청을 보낸다.
3. 서버에서 필요한 자원을 다운로드 하면서 HTML DOM 를 구성
4. 다운로드한 css 및 자원들을 가지고 HTML DOM 트리에 적용한다.

서버에 요청을 보내고 응답을 받아 처리하는 과정은 많은 단계를 거치고, 많은 비용이 발생. 필요한 부분만 갱신하면 매우 효율적.
이를 위해 나온 기술이 AJAX(Asynchronous JavaScript and XML)

## Javascript의 `this`

- Java의 `this` 는 해당 클래스을 가리키는 레퍼런스 변수.
- Javascript의 `this` 는 어떤 런타임 환경인지, 누가 호출했는지, 클래스 혹은 함수 내부에서 호출했는지, 외부에서 호출했는지 등에 따라 동적으로 바뀜.
  - 웹 브라우저에서의 전역 this 는 `window`
  - nodeJs 에서 전역 this 는 `빈 객체`

