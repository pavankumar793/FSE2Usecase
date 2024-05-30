package com.fse2.usecase.applicationgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ApplicationGateway {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationGateway.class, args);
	}
}