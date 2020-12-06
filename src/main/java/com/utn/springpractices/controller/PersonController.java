package com.utn.springpractices.controller;

import com.utn.springpractices.model.DTO.PersonDto;
import com.utn.springpractices.model.Person;
import com.utn.springpractices.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        return person
                .map(ResponseEntity::ok)
                .orElseGet( () -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Manera funcional - Buen uso del Optional
    }

    /* Optional: filter by firstname */
    @GetMapping("/")
    public ResponseEntity<List<Person>> getAll(@RequestParam (required = false) String firstname){
        List<Person> personList = personService.getAll(firstname);
        return personList.size() > 0 ? ResponseEntity.ok(personList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deleteById(@PathVariable Integer id) {

        try {
            personService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {    // Excepcion que informa que no existe entidad con ese id
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /* Delete by firstname & lastname */
    @DeleteMapping("/")
    public ResponseEntity<Person> deleteByFirstnameAndLastname (@RequestBody PersonDto personDto) {

        if(personDto.getFirstname() == null || personDto.getLastname() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Integer rows = personService.deleteByFirstnameAndLastname(
            Person.of(
                personDto.getFirstname(),
                personDto.getLastname()
            )
        );

        return rows == 0 ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<Person> update(@RequestBody Person person) {
        Optional<Person> p = personService.getById(person.getId());
        return p.isPresent() ? ResponseEntity.ok(personService.update(person)) :  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/")
    public ResponseEntity<Person> add(@RequestBody PersonDto personDto) {
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
