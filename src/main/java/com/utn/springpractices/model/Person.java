package com.utn.springpractices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data   /* @Data abarca Getter, Setter, RequiredArgsConstructor, toString, EqualsAndHashCode */
public class Person {

    private String firstname;
    private String lastname;
}
