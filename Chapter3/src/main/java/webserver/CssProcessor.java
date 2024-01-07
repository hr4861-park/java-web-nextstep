package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CssProcessor implements HttpRequestProcessor {

  private final Logger logger = LoggerFactory.getLogger(CssProcessor.class);

  private final HttpRequest request;

  public CssProcessor(final HttpRequest request) {
    this.request = request;
  }

  @Override
  public void process(final OutputStream output) {
    try (DataOutputStream stream = new DataOutputStream(output)) {
      byte[] body = Files.readAllBytes(new File("./Chapter3/webapp" + request.url()).toPath());
      stream.writeBytes("HTTP/1.1 200 OK \r\n");
      stream.writeBytes("Content-Type: text/css\r\n");
      stream.writeBytes("Content-Length: " + body.length + "\r\n");
      stream.writeBytes("\r\n");
      stream.write(body, 0, body.length);
      stream.flush();
    } catch (IOException e) {
      logger.error("Raise error on process: ", e);
    }
  }
}
