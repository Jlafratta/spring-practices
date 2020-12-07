package com.utn.springpractices.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor  /* Requerido para JPA */
@Data   /* @Data abarca Getter, Setter, RequiredArgsConstructor, toString, EqualsAndHashCode */
@RequiredArgsConstructor(staticName = "of") /* Para la creacion con dto */
@Entity /* Declaro la entidad de persistencia para JPA */
@Builder
public class Person {

    @Id /* FK (JPA) */
    @GeneratedValue(strategy = GenerationType.IDENTITY)  /* AUTOINCREMENT (JPA) */
    private Integer id;

    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
}
