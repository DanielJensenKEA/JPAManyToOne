package com.example.jpamanytoone.controller;

import com.example.jpamanytoone.model.Kommune;
import com.example.jpamanytoone.model.Region;
import com.example.jpamanytoone.repository.KommuneRepository;
import com.example.jpamanytoone.service.ApiServiceGetKommuner;
import com.example.jpamanytoone.service.ApiServiceGetKommunerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*") // Så frontend 'javascript region html form' kan nå vores endpoints
//@CrossOrigin(origins = "http://localhost:63342", methods = {RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.GET} )
@RestController
public class KommuneRestController {
    @Autowired
    ApiServiceGetKommuner apiServiceGetKommuner;
    @Autowired
    KommuneRepository kommuneRepository;

    @GetMapping("/getkommuner")
    public List<Kommune> getKommuner() {
        List<Kommune> listKommuner = apiServiceGetKommuner.getKommuner();
        return listKommuner;
    }
    /*
    //Til Javascript region html form ( FRONTEND )
     */
    @PostMapping("/kommune")
    @ResponseStatus(HttpStatus.CREATED)
    public Kommune postKommune(@RequestBody Kommune kommune) {
        System.out.println(kommune);
        return kommuneRepository.save(kommune);
    }

    @GetMapping("/getlocalkommuner")
    public List<Kommune> getLocalKommuner() {
        List<Kommune> localListKom = kommuneRepository.findAll();
        if (!localListKom.isEmpty()) {
            return localListKom;
        } else {
            throw new NullPointerException("!BACKEND KommuneRestController! List was empty. !BACKEND KommuneRestController!");
        }
    }
    @DeleteMapping("/kommune/{kode}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> deleteKommune(@PathVariable String kode) {
        Optional<Kommune> orgKommune = kommuneRepository.findById(kode);
        if (orgKommune.isPresent()) {
            kommuneRepository.deleteById(kode);
            return ResponseEntity.ok("Kommune deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kommune not found.");
        }
    }
    @PutMapping("/kommune/{kode}")
    public ResponseEntity<Kommune> updateKommune(@PathVariable String kode,
                                                 @RequestBody Kommune updatedKommune) {
        Optional<Kommune> orgKommune = kommuneRepository.findById(kode); // Vi finder eksisterende kommune vi ønsker at overskrive
        if (orgKommune.isPresent()) {
            Kommune existingKom = orgKommune.get();

            existingKom.setNavn(updatedKommune.getNavn()); //Vi overskriver existing med den kommune vi får fra vores frontend
            existingKom.setHref(updatedKommune.getHref());
            existingKom.setHrefPhoto(updatedKommune.getHrefPhoto());
            existingKom.setRegion(updatedKommune.getRegion());

            Kommune savedKom = kommuneRepository.save(existingKom); // Vi gemmer opdaterede kommune til vores database med JPA.
            return ResponseEntity.ok(savedKom); // Alt er godt
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //Alt er ikke godt.
        }
    }

}
