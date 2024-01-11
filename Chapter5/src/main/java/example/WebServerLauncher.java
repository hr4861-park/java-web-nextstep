package example;

import java.io.File;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class WebServerLauncher {

  public static void main(String[] args) throws LifecycleException {
    String webAppLocation = "Chapter5/webapp/";
    Tomcat tomcat = new Tomcat();
    String port = System.getenv("PORT");
    if (port == null || port.isEmpty()) {
      port = "8080";
    }

    tomcat.setPort(Integer.parseInt(port));
    Connector connector = tomcat.getConnector();
    connector.setURIEncoding("UTF-8");
    tomcat.addWebapp("/", new File(webAppLocation).getAbsolutePath());
    System.out.println(
        "configuring app with basedir: " + new File(webAppLocation).getAbsolutePath());
    tomcat.start();
    tomcat.getServer().await();
  }
}
