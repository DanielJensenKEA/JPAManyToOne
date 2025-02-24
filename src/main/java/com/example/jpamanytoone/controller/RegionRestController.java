package com.example.jpamanytoone.controller;

import com.example.jpamanytoone.model.Region;
import com.example.jpamanytoone.repository.RegionRepository;
import com.example.jpamanytoone.service.ApiServiceGetRegioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*") // Så frontend 'javascript region html form' kan nå vores endpoints
@RestController
public class RegionRestController {
    @Autowired
    ApiServiceGetRegioner apiServiceGetRegioner;
    @Autowired
    RegionRepository regionRepository;

    @GetMapping("/getregioner")
    public List<Region> getRegioner() {
        List<Region> listRegioner = apiServiceGetRegioner.getRegioner();
        return listRegioner;
    }


    /*
    //Til Javascript region html form ( FRONTEND )
     */
    @PostMapping("/region")
    @ResponseStatus(HttpStatus.CREATED)
    public Region postRegion(@RequestBody Region region) {
        System.out.println(region);
        return regionRepository.save(region);
    }
    @PostMapping("/region2")
    public ResponseEntity<String> postRegion2(@RequestParam String kode, @RequestParam String navn) {
        return ResponseEntity.ok("Received kode: "+kode+", navn: "+navn);
    }

}
