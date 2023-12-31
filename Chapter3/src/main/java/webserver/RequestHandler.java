package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import model.HttpMethod;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {

  private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

  private final Socket connection;

  public RequestHandler(Socket connectionSocket) {
    this.connection = connectionSocket;
  }

  @Override
  public void run() {
    log.debug(
        "New Client Connect! Connected IP : {}, Port : {}",
        connection.getInetAddress(),
        connection.getPort());

    try (InputStream in = connection.getInputStream();
        OutputStream out = connection.getOutputStream()) {
      BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
      String httpRequest = bufferedReader.readLine();
      log.info("input: {}", httpRequest);

      HttpMethod method = new HttpMethodFactory().fromHeader(httpRequest);
      String[] splited = httpRequest.split(" ");
      String path = splited[1];

      final String[] urlWithQueryParam = path.split("\\?");
      if (urlWithQueryParam.length >= 1 && ("/user/create".equals(urlWithQueryParam[0]))) {
        Map<String, String> queryParam = HttpRequestUtils.parseQueryString(urlWithQueryParam[1]);
        User user =
            User.of(
                queryParam.get("userId"),
                queryParam.get("password"),
                queryParam.get("name"),
                queryParam.get("email"));
        log.info("saved user: {}", user);
      }
      DataOutputStream dos = new DataOutputStream(out);
      byte[] body = Files.readAllBytes(new File("./Chapter3/webapp" + path).toPath());
      response200Header(dos, body.length);
      responseBody(dos, body);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
    try {
      dos.writeBytes("HTTP/1.1 200 OK \r\n");
      dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
      dos.writeBytes("\r\n");
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  private void responseBody(DataOutputStream dos, byte[] body) {
    try {
      dos.write(body, 0, body.length);
      dos.flush();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
}
