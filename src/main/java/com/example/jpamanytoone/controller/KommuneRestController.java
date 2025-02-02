package com.example.jpamanytoone.controller;

import com.example.jpamanytoone.model.Kommune;
import com.example.jpamanytoone.service.ApiServiceGetKommuner;
import com.example.jpamanytoone.service.ApiServiceGetKommunerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KommuneRestController {
    @Autowired
    ApiServiceGetKommuner apiServiceGetKommuner;

    @GetMapping("/getkommuner")
    public List<Kommune> getKommuner() {
        List<Kommune> listKommuner = apiServiceGetKommuner.getKommuner();
        return listKommuner;
    }
}
