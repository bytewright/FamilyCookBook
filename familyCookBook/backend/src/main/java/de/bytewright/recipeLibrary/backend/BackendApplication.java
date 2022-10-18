package de.bytewright.recipeLibrary.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import de.bytewright.recipeLibrary.api.generated.spring_autoconfiguration.SpringConfiguration;

@SpringBootApplication
@Import(SpringConfiguration.class)
public class BackendApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(BackendApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }
}
