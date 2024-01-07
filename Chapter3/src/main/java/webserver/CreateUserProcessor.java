package webserver;

import db.DataBase;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import model.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserProcessor implements HttpRequestProcessor {

  private static final Logger logger = LoggerFactory.getLogger(CreateUserProcessor.class);
  private final HttpRequest request;

  public CreateUserProcessor(HttpRequest request) {
    this.request = request;
  }

  @Override
  public void process(final OutputStream output) {
    User user = User.fromMap(request.body());
    logger.info("Create user: {}", user);
    DataBase.addUser(user);
    try (DataOutputStream stream = new DataOutputStream(output)) {
      stream.writeBytes("HTTP/1.1 302 Redirect \r\n");
      stream.writeBytes("Location: /index.html \r\n");
      stream.writeBytes("\r\n");
    } catch (IOException e) {
      logger.error("Raise error on process ", e);
    }
  }
}
