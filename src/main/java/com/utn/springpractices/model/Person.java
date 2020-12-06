package com.utn.springpractices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor  /* Requerido para JPA */
@Data   /* @Data abarca Getter, Setter, RequiredArgsConstructor, toString, EqualsAndHashCode */
@Entity /* Declaro la entidad de persistencia para JPA */
public class Person {

    @Id /* FK (JPA) */
    @GeneratedValue  /* AUTOINCREMENT (JPA) */
    private Integer id;

    private String firstname;
    private String lastname;
}
