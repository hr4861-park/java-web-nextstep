package http;

import java.util.Map;
import util.HttpRequestUtils;

public class HttpCookie {

    private final Map<String, String> cookies;

    public HttpCookie(String cookie) {
        cookies = HttpRequestUtils.parseCookies(cookie);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }
}
