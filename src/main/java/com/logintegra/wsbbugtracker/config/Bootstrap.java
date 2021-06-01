package com.logintegra.wsbbugtracker.config;

import com.logintegra.wsbbugtracker.people.Person;
import com.logintegra.wsbbugtracker.people.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Bootstrap implements InitializingBean {

    private static final String DEFAULT = "DEFAULT";
    private final Logger log = LoggerFactory.getLogger(Bootstrap.class);
    private final PersonService personService;

    @Value("${my.admin.username}")
    private String adminUsername;

    @Value("${my.admin.password}")
    private String adminPassword;

    public Bootstrap(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("Przygotowujemy aplikację...");

        createDefaultUser();

        log.info("Skończyliśmy przygotowywać aplikację!");
    }


    private void createDefaultUser() {
        if (personService.findPersonByUsername(adminUsername) == null) {
            log.info("Tworzymy użytkownika administracyjnego -- " + adminUsername + "...");
            personService.saveUser(new Person(adminUsername, adminPassword, adminUsername, adminUsername + "@logintegra.com"));
        }

        if (personService.findPersonByUsername(DEFAULT) != null) {
            log.info("Istnieją już jacyś użytkownicy, nie tworzymy użytkownika domyślnego");
            return;
        }

        Person defaultUser = new Person(DEFAULT, DEFAULT, DEFAULT, DEFAULT);
        personService.saveUser(defaultUser);
    }
}
