package com.utn.springpractices.controller;

import com.utn.springpractices.exception.notExist.PersonNotExistException;
import com.utn.springpractices.model.DTO.PersonDto;
import com.utn.springpractices.model.Person;
import com.utn.springpractices.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/person", produces = "application/json")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ApiOperation(value = "Get by ID", response = Person.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved entity"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you are trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Integer id) {

        try {
            return ResponseEntity.ok(personService.getById(id));
        } catch (PersonNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Optional<Person> person = personService.getById(id);
        /* return person.isPresent() ? ResponseEntity.ok(person.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */
        /*return person
                .map(ResponseEntity::ok)
                .orElseGet( () -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Manera funcional - Buen uso del Optional */
    }

    /* Optional: filter by firstname */
    @ApiOperation(value = "Get all. Optionally can filter by firstname")
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

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@RequestBody PersonDto personDto, @PathVariable Integer id) {
        if(personDto.getFirstname() == null || personDto.getLastname() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try {
            Person p = personService.getById(id);
            p.setFirstname(personDto.getFirstname());
            p.setLastname(personDto.getLastname());

            return ResponseEntity.ok(personService.update(p));
        } catch (PersonNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        /*
        Optional<Person> p = personService.getById(person.getId());
        return p.isPresent() ? ResponseEntity.ok(personService.update(person)) :  ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */
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
