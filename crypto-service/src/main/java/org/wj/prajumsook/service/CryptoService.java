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
public class CryptoService {

    private RestTemplate restTemplate;
    private CircuitBreakerFactory circuitBreakerFactory;

    @Value("${crypto.url}")
    private String cryptoUrl;

    @Autowired
    public CryptoService(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public Optional<ResponseEntity> getCryptos() {
        String url = cryptoUrl + "?formatted=false&scrIds=all_cryptocurrencies_us&start=0";
        log.info("Getting cryptocurrency");
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
