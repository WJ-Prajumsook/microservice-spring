package org.wj.prajumsook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wj.prajumsook.service.CurrencyService;

@Slf4j
@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity getCurrency(@PathVariable("symbol")String symbol) {
        return currencyService.getCurrency(symbol).get();
    }
}
