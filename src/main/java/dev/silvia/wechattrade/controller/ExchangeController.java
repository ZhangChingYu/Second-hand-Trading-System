package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ExchangeController {
    @Autowired
    @Resource
    private IOrderService service;

    @RequestMapping(value ="/orders",method = RequestMethod.GET)
    public ResponseEntity<?> build(@RequestBody ExRequestDto request) {


            return ResponseEntity.ok("cds");

    }
}
