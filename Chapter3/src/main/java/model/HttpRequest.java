package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import util.HttpRequestUtils;
import util.IOUtils;

public record HttpRequest(
    HttpMethod method, String url, Map<String, String> headers, Map<String, String> body) {

  public static HttpRequest fromStreams(InputStream stream) throws IOException {
    BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
    String request = bufferedReader.readLine();
    final String[] split = request.split(" ");
    Map<String, String> headers = getHeaders(bufferedReader);
    Map<String, String> bodies = getBodies(bufferedReader, headers);
    return new HttpRequest(HttpMethod.valueOf(split[0]), split[1], headers, bodies);
  }

  private static Map<String, String> getBodies(
      final BufferedReader stream, final Map<String, String> headers) throws IOException {
    Map<String, String> bodies = new HashMap<>();
    if (headers.containsKey("Content-Length")) {
      int length = Integer.parseInt(headers.get("Content-Length"));
      String bodyString = IOUtils.readData(stream, length);
      bodies = HttpRequestUtils.parseQueryString(bodyString);
    }
    return bodies;
  }

  private static Map<String, String> getHeaders(final BufferedReader stream) throws IOException {
    Map<String, String> headers = new HashMap<>();
    String header = stream.readLine();
    while (!header.isEmpty()) {
      final String[] splitedHeader = header.split(":");
      headers.put(splitedHeader[0].trim(), splitedHeader[1].trim());
      header = stream.readLine();
    }
    return headers;
  }
}
