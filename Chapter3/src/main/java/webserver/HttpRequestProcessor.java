package webserver;

import java.io.IOException;
import model.HttpRequest;

public interface HttpRequestProcessor {

  static HttpRequestProcessor createProcessor(HttpRequest request) {
    return switch (request) {
      case DEFAULT -> new DefaultProcessor();
      case CREATE_USER -> new CreateUserProcessor();
    };
  }

  byte[] getResponse(String method, String url) throws IOException;
}
