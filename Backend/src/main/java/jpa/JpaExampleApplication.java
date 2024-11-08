package jpa;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableScheduling
public class JpaExampleApplication {

	// Injecting the upload directory path from application.properties
	@Value("${file.upload-dir:uploads}")
	private String uploadDir;

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(5); // Set the number of threads for scheduling
		scheduler.setThreadNamePrefix("ScheduledTask-");
		return scheduler;
	}

	// Ensure the upload directory exists when the application starts
	@PostConstruct
	public void init() {
		try {
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
				System.out.println("Upload directory created at: " + uploadDir);
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not create upload directory", e);
		}
	}

	// Add any additional startup logic
	@Bean
	public CommandLineRunner initData() {
		return args -> {
			System.out.println("Application has started successfully!");
			// Additional initialization logic can be added here
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(JpaExampleApplication.class, args);
	}
}
