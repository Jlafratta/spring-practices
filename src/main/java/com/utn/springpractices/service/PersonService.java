package com.utn.springpractices.service;

import com.utn.springpractices.exception.notFound.PersonNotFoundException;
import com.utn.springpractices.exception.notFound.PetNotFoundException;
import com.utn.springpractices.model.DTO.PersonDto;
import com.utn.springpractices.model.Person;
import com.utn.springpractices.model.Pet;
import com.utn.springpractices.repository.PersonRepository;
import com.utn.springpractices.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PetRepository petRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, PetRepository petRepository) {
        this.personRepository = personRepository;
        this.petRepository = petRepository;
    }

    public List<Person> getAll(String firstname) {
        return isNull(firstname) ? personRepository.findAll() : personRepository.findByFirstname(firstname);
    }

    public Person add(PersonDto dto) {
        return personRepository.save(Person.buildFromDTO(dto));
    }

    public Person getById(Integer id) {
        return personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
    }

    public Person update(Integer id, PersonDto dto) {
        return personRepository
                .findById(id)
                .map(person -> updateInstance(person, dto))
                .orElseThrow(PersonNotFoundException::new);
    }

    private Person updateInstance(Person person, PersonDto dto) {
        person.setFirstname(dto.getFirstname());
        person.setLastname(dto.getLastname());
        return personRepository.save(person);
    }

    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    @Transactional /* Aplicado para garantizar que se ejecute de manera transaccional, y no romper las propiedades ACID de la bdd */
    public Integer deleteByFirstnameAndLastname(PersonDto dto) {
        return personRepository.deleteByFirstnameAndLastname(dto.getFirstname(), dto.getLastname());
    }

    public Person assignPet(Integer id, Integer petId) {
        return personRepository
                .findById(id)
                .map(person -> updateAssignPet(person, petId))
                .orElseThrow(PersonNotFoundException::new);
    }

    private Person updateAssignPet(Person person, Integer petId) {
        Pet pet = petRepository
                .findById(petId)
                .orElseThrow(PetNotFoundException::new);

        person.addPet(pet);
        
        return personRepository.save(person);
    }
}
