package org.wj.prajumsook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class FutureService {

    private RestTemplate restTemplate;
    private CircuitBreakerFactory circuitBreakerFactory;

    @Value("${future.url}")
    private String futureUrl;

    @Autowired
    public FutureService(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public Optional<ResponseEntity> getFuture(String symbol) {
        String url = futureUrl + "?formatted=false&symbols=" + symbol;
        log.info("Getting future");
        return circuitBreakerFactory.create("slow").run(() -> {
            ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
            log.info(responseEntity.getBody().toString());
            return Optional.ofNullable(responseEntity);
        }, throwable -> Optional.ofNullable(fallBack()));
    }

    private ResponseEntity fallBack() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
