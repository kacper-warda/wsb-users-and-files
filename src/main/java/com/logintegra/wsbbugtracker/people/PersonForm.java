package com.logintegra.wsbbugtracker.people;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PersonForm {

    Long id;

    @NotBlank
    String username;

    @NotBlank
    String name;

    @NotBlank
    String email;

    PersonForm(Person person) {
        this.id = person.id;
        this.username = person.username;
        this.name = person.name;
        this.email = person.email;
    }

}
