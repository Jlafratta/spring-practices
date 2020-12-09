package com.utn.springpractices.controller;

import com.utn.springpractices.model.DTO.PetDto;
import com.utn.springpractices.model.Pet;
import com.utn.springpractices.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pet", produces = "application/json")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) { this.petService = petService; }

    @PostMapping("/")
    public ResponseEntity<Pet> add(@Valid @RequestBody PetDto petDto) {
        Pet newPet = petService.add(petDto);

        return ResponseEntity
                .created(getLocation(newPet))
                .build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Pet>> getAll() {
        List<Pet> petList = petService.getAll();
        return petList.size() > 0 ? ResponseEntity.ok(petList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private URI getLocation(Pet newPet) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPet.getId())
                .toUri();
    }

}
