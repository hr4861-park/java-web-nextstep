package webserver;

import db.DataBase;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import model.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class ShowUserListProcessor implements HttpRequestProcessor {

  private static final Logger logger = LoggerFactory.getLogger(ShowUserListProcessor.class);

  private final HttpRequest request;

  public ShowUserListProcessor(final HttpRequest request) {
    this.request = request;
  }

  private static byte[] createResponse() {
    final Collection<User> users = DataBase.findAll();

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<table border='1'>");
    users.forEach(
        user ->
            stringBuilder
                .append("<tr>")
                .append("<td>")
                .append(user.getUserId())
                .append("</td>")
                .append("<td>")
                .append(user.getName())
                .append("</td>")
                .append("<td>")
                .append(user.getEmail())
                .append("</td>"));
    stringBuilder.append("</table>");
    return stringBuilder.toString().getBytes();
  }

  @Override
  public void process(final OutputStream output) {
    final String cookieString = request.headers().get("Cookie");
    if (cookieString == null) {
      noHaveCookie(output);
    }

    final String loginedCookie = HttpRequestUtils.parseCookies(cookieString).get("logined");
    if (loginedCookie == null) {
      noHaveCookie(output);
    }

    if (!Boolean.parseBoolean(loginedCookie)) {
      noHaveCookie(output);
    }

    byte[] response = createResponse();
    try (DataOutputStream stream = new DataOutputStream(output)) {
      stream.writeBytes("HTTP/1.1 200 OK \r\n");
      stream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      stream.writeBytes("Content-Length: " + response.length + "\r\n");
      stream.writeBytes("\r\n");
      stream.write(response, 0, response.length);
      stream.flush();
    } catch (IOException e) {
      logger.error("Raise error on process: ", e);
    }
  }

  private void noHaveCookie(final OutputStream output) {
    try (DataOutputStream stream = new DataOutputStream(output)) {
      stream.writeBytes("HTTP/1.1 302 Redirect \r\n");
      stream.writeBytes("Location: /index.html \r\n");
      stream.writeBytes("\r\n");
    } catch (IOException e) {
      logger.error("Raise error on process ", e);
    }
  }
}
