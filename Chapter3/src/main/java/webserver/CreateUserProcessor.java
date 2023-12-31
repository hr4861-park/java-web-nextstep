package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import model.User;
import util.HttpRequestUtils;

public class CreateUserProcessor implements HttpRequestProcessor {

  @Override
  public byte[] getResponse(final String method, final String url) throws IOException {
    final String[] urlWithQueryParam = url.split("\\?");
    if (urlWithQueryParam.length >= 1 && ("/user/create".equals(urlWithQueryParam[0]))) {
      Map<String, String> queryParam = HttpRequestUtils.parseQueryString(urlWithQueryParam[1]);
      User user =
          User.of(
              queryParam.get("userId"),
              queryParam.get("password"),
              queryParam.get("name"),
              queryParam.get("email"));
    }
    return Files.readAllBytes(new File("./Chapter3/webapp" + url).toPath());
  }
}
