package webserver;

import java.io.OutputStream;
import java.util.Objects;
import model.HttpMethod;
import model.HttpRequest;

public interface HttpRequestProcessor {

  static HttpRequestProcessor createProcessor(HttpRequest request) {
    if (Objects.requireNonNull(request.method()) == HttpMethod.POST) {
      if (request.url().equals("/user/create")) {
        return new CreateUserProcessor(request);
      } else if (request.url().equals("/user/login")) {
        return new LoginUserProcessor(request);
      }
    }
    return new DefaultProcessor(request);
  }

  void process(OutputStream output);
}
