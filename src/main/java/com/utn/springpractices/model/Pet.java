package com.utn.springpractices.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.utn.springpractices.model.DTO.PetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference  /* Indica que el obj es una referencia para la entidad. Previene entrar en bucle al acceder al attr */
    private Person person;

    public static Pet buildFromDTO(PetDto petDto) {
        return Pet.builder()
                .name(petDto.getName())
                .weight(petDto.getWeight())
                .person(petDto.getPerson())
                .build();
    }
}
