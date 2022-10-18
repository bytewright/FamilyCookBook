package de.bytewright.recipeLibrary.webserver;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WicketFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import de.bytewright.recipeLibrary.webserver.frontend.WicketApplicationConfiguration;

@SpringBootApplication
public class WebserverStarterApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebserverStarterApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(WebserverStarterApplication.class, args);
  }

  @Bean
  public FilterRegistrationBean<WicketFilter> wicketFilterRegistration(WicketFilter wicketFilter,
      WicketApplicationConfiguration wicketApplication) {
    FilterRegistrationBean<WicketFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(wicketFilter);
    registration.addUrlPatterns("/*");
    registration.addInitParameter("applicationClassName", wicketApplication.getClass().getCanonicalName());
    registration.addInitParameter("configuration", RuntimeConfigurationType.DEVELOPMENT.name());
    registration.setName("WicketApplicationGCM");
    registration.setOrder(1);
    LOGGER.info("Created Wicket Filter Registration: {}", registration);
    return registration;
  }
}
