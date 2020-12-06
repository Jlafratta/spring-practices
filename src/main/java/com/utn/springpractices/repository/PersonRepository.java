package com.utn.springpractices.repository;

import com.utn.springpractices.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findByFirstname(String firstname);

    Integer deleteByFirstnameAndLastname(String firstname, String lastname);
}
