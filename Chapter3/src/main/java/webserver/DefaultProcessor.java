package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultProcessor implements HttpRequestProcessor {

  private static final Logger logger = LoggerFactory.getLogger(DefaultProcessor.class);
  private final HttpRequest request;

  public DefaultProcessor(final HttpRequest request) {
    this.request = request;
  }

  @Override
  public void process(final OutputStream output) {
    try (DataOutputStream dos = new DataOutputStream(output)) {
      byte[] response = Files.readAllBytes(new File("./Chapter3/webapp" + request.url()).toPath());
      response200Header(dos, response.length);
      responseBody(dos, response);
    } catch (IOException e) {
      logger.error("Raise error on process: ", e);
    }
  }

  private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
    try {
      dos.writeBytes("HTTP/1.1 200 OK \r\n");
      dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
      dos.writeBytes("\r\n");
    } catch (IOException e) {
      logger.error("Raise error on response200Header: ", e);
    }
  }

  private void responseBody(DataOutputStream dos, byte[] body) {
    try {
      dos.write(body, 0, body.length);
      dos.flush();
    } catch (IOException e) {
      logger.error("Raise error on responseBody: ", e);
    }
  }
}
