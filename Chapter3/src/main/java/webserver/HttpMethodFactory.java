package webserver;

import model.HttpMethod;

public class HttpMethodFactory {

  HttpMethod fromHeader(String httpRequest) {
    String[] splited = httpRequest.split(" ");
    return HttpMethod.valueOf(splited[0]);
  }
}
