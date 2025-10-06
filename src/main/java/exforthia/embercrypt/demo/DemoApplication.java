package exforthia.embercrypt.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "exforthia.embercrypt")
@EnableJpaRepositories(basePackages = "exforthia.embercrypt.persistence.repositories")
@EntityScan(basePackages = "exforthia.embercrypt.persistence.entities")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
