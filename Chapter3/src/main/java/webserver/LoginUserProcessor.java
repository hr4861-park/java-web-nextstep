package webserver;

import db.DataBase;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import model.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUserProcessor implements HttpRequestProcessor {

  private final Logger logger = LoggerFactory.getLogger(LoginUserProcessor.class);

  private final HttpRequest request;

  public LoginUserProcessor(final HttpRequest request) {
    this.request = request;
  }

  private static void processLoginFailure(final DataOutputStream stream) throws IOException {
    byte[] response =
        Files.readAllBytes(new File("./Chapter3/webapp/user/login_failed.html").toPath());
    stream.writeBytes("HTTP/1.1 200 OK \r\n");
    stream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
    stream.writeBytes("Content-Length: " + response.length + "\r\n");
    stream.writeBytes("\r\n");
    stream.write(response, 0, response.length);
  }

  @Override
  public void process(final OutputStream output) {
    try (DataOutputStream stream = new DataOutputStream(output)) {
      final User exists = DataBase.findUserById(request.body().get("userId"));
      if (exists == null) {
        processLoginFailure(stream);
        return;
      }

      if (!exists.getPassword().equals(request.body().get("password"))) {
        processLoginFailure(stream);
        return;
      }

      processLoginSuccess(stream);
    } catch (IOException e) {
      logger.error("Raise error on response200Header: ", e);
    }
  }

  private void processLoginSuccess(final DataOutputStream stream) throws IOException {
    stream.writeBytes("HTTP/1.1 302 Redirect \r\n");
    stream.writeBytes("Set-Cookie: logined=true \r\n");
    stream.writeBytes("Location: /index.html \r\n");
    stream.writeBytes("\r\n");
  }
}
