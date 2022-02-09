package com.ead.authuser.configs;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	static final int TIMEOUT = 5000;
	
	@LoadBalanced //para utilizar balanceamento de carga do spring clound
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
			.setConnectTimeout(Duration.ofMillis(TIMEOUT)) //Duração de resposta para período de resposta e leitura
			.setReadTimeout(Duration.ofMillis(TIMEOUT))
			.build();
	}
}
