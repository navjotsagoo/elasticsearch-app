package io.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="io.pivotal")
public class ElasticsearchAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchAppApplication.class, args);
	}
}
