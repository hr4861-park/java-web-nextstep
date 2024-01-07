package webserver;

import java.io.OutputStream;
import java.util.Objects;
import model.HttpMethod;
import model.HttpRequest;

public interface HttpRequestProcessor {

  static HttpRequestProcessor createProcessor(HttpRequest request) {
    HttpMethod httpMethod = Objects.requireNonNull(request.method());
    if (httpMethod == HttpMethod.POST) {
      if (request.url().equals("/user/create")) {
        return new CreateUserProcessor(request);
      } else if (request.url().equals("/user/login")) {
        return new LoginUserProcessor(request);
      }
    } else if (httpMethod == HttpMethod.GET) {
      if (request.url().equals("/user/list")) {
        return new ShowUserListProcessor(request);
      } else if (request.url().endsWith(".css")) {
        return new CssProcessor(request);
      }
    }
    return new DefaultProcessor(request);
  }

  void process(OutputStream output);
}
