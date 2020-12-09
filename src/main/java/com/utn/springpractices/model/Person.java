package com.utn.springpractices.model;

import com.utn.springpractices.model.DTO.PersonDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "person", cascade = CascadeType.DETACH)
    private List<Pet> pets;

    public void addPet(Pet pet) {
        this.pets.add(pet);
        pet.setPerson(this);
    }

    public static Person buildFromDTO(PersonDto p) {
        return Person.builder()
                .firstname(p.getFirstname())
                .lastname(p.getLastname())
                .pets(p.getPets())
                .build();
    }
}
