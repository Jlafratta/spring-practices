package com.utn.springpractices.model;

import com.utn.springpractices.model.DTO.PersonDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor  /* Requerido para JPA */
@Data   /* @Data abarca Getter, Setter, RequiredArgsConstructor, toString, EqualsAndHashCode */
@Entity /* Declaro la entidad de persistencia para JPA */
@Builder
public class Person {

    @Id /* FK (JPA) */
    @GeneratedValue(strategy = GenerationType.IDENTITY)  /* AUTOINCREMENT (JPA) */
    private Integer id;

    private String firstname;

    private String lastname;

    public static Person buildFromDTO(PersonDto p) {
        return Person.builder()
                .firstname(p.getFirstname())
                .lastname(p.getLastname())
                .build();
    }
}
