package com.utn.springpractices.controller;

import com.utn.springpractices.exception.notFound.PersonNotFoundException;
import com.utn.springpractices.model.DTO.PersonDto;
import com.utn.springpractices.model.Person;
import com.utn.springpractices.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
            @ApiResponse(code = 404, message = "There wasn't any person according to the request")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Integer id) {
            return ResponseEntity.ok(personService.getById(id));
    }

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
            throw new PersonNotFoundException();
        }
    }

    /* Delete by firstname & lastname */
    @DeleteMapping("/")
    public ResponseEntity<Person> deleteByFirstnameAndLastname (@Valid @RequestBody PersonDto personDto) {
        Integer rows = personService.deleteByFirstnameAndLastname(personDto);
        return rows == 0 ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@Valid @RequestBody PersonDto personDto, @PathVariable Integer id) {

        return ResponseEntity.ok(personService.update(id, personDto));
    }

    @PostMapping("/")
    public ResponseEntity<Person> add(@Valid @RequestBody PersonDto personDto) {

        Person newPerson = personService.add(personDto);

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
