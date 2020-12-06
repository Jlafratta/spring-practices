package com.utn.springpractices.service;

import com.utn.springpractices.model.Person;
import com.utn.springpractices.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public void add(Person person) {
        personRepository.save(person);
    }

    public Optional<Person> getById(Integer id) {
        return personRepository.findById(id);
    }
}
