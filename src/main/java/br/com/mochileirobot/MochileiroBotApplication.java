package br.com.mochileirobot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories({"br.com.mochileirobot.repository"})
public class MochileiroBotApplication implements CommandLineRunner {

	@Autowired
	MochileiroInitializer mochileiroInitializer;

	public static void main(String[] args) {
		SpringApplication.run(MochileiroBotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mochileiroInitializer.run();
	}
}
