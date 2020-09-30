package com.cloud.example.clouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableEurekaServer
@SpringBootApplication
//@EnableFeignClients
public class ClouddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClouddemoApplication.class, args);
	}

}
