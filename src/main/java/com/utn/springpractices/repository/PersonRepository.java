package com.utn.springpractices.repository;

import com.utn.springpractices.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query(value = "SELECT * FROM person WHERE firstname = ?1", nativeQuery = true)
    List<Person> findByFirstname(String firstname);

    Integer deleteByFirstnameAndLastname(String firstname, String lastname);
}
