package com.utn.springpractices.controller;

import com.utn.springpractices.exception.notExist.PersonNotExistException;
import com.utn.springpractices.model.Person;
import com.utn.springpractices.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersonControllerTest {

    @Mock
    PersonService personService;

    PersonController personController;

    @Before
    public void setUp() {
        initMocks(this);
        this.personController = new PersonController(personService);
    }

    @Test
    public void testGetByIdOk() throws PersonNotExistException {
        // Mock data
        Person p = Person.builder()
                .id(1)
                .firstname("Julian")
                .lastname("Lafratta")
                .build();

        // Accionar del mock
        when(personService.getById(p.getId())).thenReturn(p);

        // Ejecuto el metodo
        ResponseEntity<Person> response = personController.getById(p.getId());

        // Verifico los retornos correspondientes
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(p, response.getBody());

        // Verifico la ejecucion del mock
        verify(personService, times(1)).getById(p.getId());
    }

    @Test
    public void testGetByIdNotFound() throws PersonNotExistException {
        // Mock data
        Integer id = anyInt();
        when(personService.getById(id)).thenThrow(new PersonNotExistException());
        personController.getById(id);
        verify(personService, times(1)).getById(id);
    }

    @Test
    public void testGetAllOk() {
        // Mock data
        List<Person> personList = new ArrayList<>();
        personList.add(
                Person.builder()
                .id(1)
                .firstname("Julian")
                .lastname("Lafratta")
                .build());
        String firstname = null;

        when(personService.getAll(firstname)).thenReturn(personList);

        ResponseEntity<List<Person>> response = personController.getAll(firstname);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personList, response.getBody());
        verify(personService, times(1)).getAll(firstname);
    }

    @Test
    public void testGetAllFilteredOk() {
        // Mock data
        List<Person> personList = new ArrayList<>();
        personList.add(
                Person.builder()
                        .id(1)
                        .firstname("Julian")
                        .lastname("Lafratta")
                        .build());
        String firstname = "Julian";

        when(personService.getAll(firstname)).thenReturn(personList);

        ResponseEntity<List<Person>> response = personController.getAll(firstname);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personList, response.getBody());
        verify(personService, times(1)).getAll(firstname);
    }

    @Test
    public void testGetAllNoContent() {
        // Mock data
        List<Person> personList = new ArrayList<>();
        String firstname = null;

        when(personService.getAll(firstname)).thenReturn(personList);

        ResponseEntity<List<Person>> response = personController.getAll(firstname);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personService, times(1)).getAll(firstname);
    }

    @Test
    public void testGetAllFilteredNoContent() {
        // Mock data
        List<Person> personList = new ArrayList<>();

        String firstname = "Julian";

        when(personService.getAll(firstname)).thenReturn(personList);

        ResponseEntity<List<Person>> response = personController.getAll(firstname);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personService, times(1)).getAll(firstname);
    }
}
