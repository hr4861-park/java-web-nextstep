package model;

import java.util.Arrays;

public enum HttpRequest {
  DEFAULT("", ""),
  CREATE_USER("POST", "/user/create");

  private final String method;
  private final String url;

  HttpRequest(String method, String url) {
    this.method = method;
    this.url = url;
  }

  public static HttpRequest fromHttpRequest(String method, String url) {

    return Arrays.stream(values())
        .filter(x -> x.url.equals(url) && x.method.equals(method))
        .findFirst()
        .orElse(DEFAULT);
  }
}
