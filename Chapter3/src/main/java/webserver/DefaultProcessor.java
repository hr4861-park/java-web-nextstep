package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DefaultProcessor implements HttpRequestProcessor {
  @Override
  public byte[] getResponse(final String method, final String url) throws IOException {
    return Files.readAllBytes(new File("./Chapter3/webapp" + url).toPath());
  }
}
