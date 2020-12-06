package com.utn.springpractices.controller;

import com.utn.springpractices.model.Person;
import com.utn.springpractices.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Optional<Person> getById(@PathVariable Integer id) {
        return personService.getById(id);
    }

    @GetMapping("/")
    public List<Person> getAll(@RequestParam (required = false) String firstname){
        return personService.getAll(firstname);
    }

    @PostMapping("/")
    public void add(@RequestBody Person person) {
        personService.add(person);
    }

}
