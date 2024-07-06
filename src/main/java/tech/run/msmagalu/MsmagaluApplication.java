package tech.run.msmagalu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsmagaluApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsmagaluApplication.class, args);
	}

}
