package com.logintegra.wsbbugtracker.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUsername(String username);

    List<Person> findAllByEnabled(Boolean enabled);

    // https://stackoverflow.com/a/32155913 :/
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("select p from Person p where p.username = :username")
    Person findLoggedUser(String username);
}
