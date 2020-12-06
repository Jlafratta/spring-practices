package com.utn.springpractices.controller;

import com.utn.springpractices.model.DTO.AddPersonDto;
import com.utn.springpractices.model.Person;
import com.utn.springpractices.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Integer id) {
        Optional<Person> person = personService.getById(id);
        /* return person.isPresent() ? ResponseEntity.ok(person.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Functional way
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> getAll(@RequestParam (required = false) String firstname){
        List<Person> personList = personService.getAll(firstname);
        return personList.size() > 0 ? ResponseEntity.ok(personList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/")
    public ResponseEntity<Person> add(@RequestBody AddPersonDto personDto) {
        Person newPerson = personService.add(
           Person.of(
                personDto.getFirstname(),
                personDto.getLastname()
           )
        );
        return ResponseEntity
                .created(getLocation(newPerson))
                .build();
    }

    /* Get location path (by id) of the person passed in params */
    private URI getLocation(Person newPerson) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPerson.getId())
                .toUri();
    }

}
