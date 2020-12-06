package com.utn.springpractices.service;

import com.utn.springpractices.model.Person;
import com.utn.springpractices.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAll(String firstname) {
        return isNull(firstname) ? personRepository.findAll() : personRepository.findByFirstname(firstname);
    }

    public Person add(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> getById(Integer id) {
        return personRepository.findById(id);
    }

    public Person update(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    @Transactional /* Aplicado para garantizar que se ejecute de manera transaccional, y no romper las propiedades ACID de la bdd */
    public Integer deleteByFirstnameAndLastname(Person person) {
        return personRepository.deleteByFirstnameAndLastname(person.getFirstname(), person.getLastname());
    }

}
