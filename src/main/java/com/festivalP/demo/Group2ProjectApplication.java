package com.festivalP.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


//나중에 db연결할 때 exclude = DataSourceAutoConfiguration.class 부분 지워야한다.
//@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Group2ProjectApplication {


	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	public static void main(String[] args) {
		SpringApplication.run(Group2ProjectApplication.class, args);

	}
}
