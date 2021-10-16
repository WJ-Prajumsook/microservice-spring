package org.wj.prajumsook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wj.prajumsook.service.CryptoService;

@Slf4j
@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllCrypto() {
        return cryptoService.getCryptos().get();
    }
}
