package com.intbee.demo.nacos.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoNacosProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoNacosProviderApplication.class, args);
	}

}
