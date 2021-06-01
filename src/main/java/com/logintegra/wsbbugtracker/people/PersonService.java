package com.logintegra.wsbbugtracker.people;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * https://medium.com/@gustavo.ponce.ch/spring-boot-spring-mvc-spring-security-mysql-a5d8545d837d
 */
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void saveUser(Person person) {
        person.password = bCryptPasswordEncoder.encode(person.password);

        personRepository.save(person);
    }

    public void saveUser(PersonForm personForm) {
        Person person = personRepository.findById(personForm.id).orElse(null);
        person.username = personForm.username;
        person.email = personForm.email;
        person.name = personForm.name;
        personRepository.save(person);

    }

    public Person findPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public void disable(Person person) {
        person.enabled = false;
        personRepository.save(person);
    }

    public void updatePassword(PasswordForm passwordForm) {
        Person person = personRepository.findById(passwordForm.id).orElse(null);
        person.password = bCryptPasswordEncoder.encode(passwordForm.password);
        personRepository.save(person);

    }
}
