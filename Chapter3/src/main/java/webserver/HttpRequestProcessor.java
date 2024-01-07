package webserver;

import java.io.OutputStream;
import java.util.Objects;
import model.HttpRequest;

public interface HttpRequestProcessor {

  static HttpRequestProcessor createProcessor(HttpRequest request) {
    switch (Objects.requireNonNull(request.method())) {
      case POST -> {
        if (request.url().equals("/user/create")) {
          return new CreateUserProcessor(request);
        } else if (request.url().equals("/user/login")) {
          return new LoginUserProcessor(request);
        }
      }
      case GET -> {
        if (request.url().equals("/user/list")) {
          return new ShowUserListProcessor(request);
        }
      }
    }
    return new DefaultProcessor(request);
  }

  void process(OutputStream output);
}
