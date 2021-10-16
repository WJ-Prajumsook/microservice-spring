package org.wj.prajumsook;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class FutureServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutureServiceApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	@ConditionalOnBean(CircuitBreakerFactory.class)
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build()
		);
	}

}
