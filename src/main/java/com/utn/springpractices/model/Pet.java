package com.utn.springpractices.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utn.springpractices.model.DTO.PetDto;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference  /* Indica que el obj es una referencia para la entidad. Previene entrar en bucle al acceder al attr */
    @ToString.Exclude
    private Person person;

    public static Pet buildFromDTO(PetDto petDto) {
        return Pet.builder()
                .name(petDto.getName())
                .weight(petDto.getWeight())
                .person(petDto.getPerson())
                .build();
    }
}
