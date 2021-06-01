package com.logintegra.wsbbugtracker.config;

import com.logintegra.wsbbugtracker.people.Person;
import com.logintegra.wsbbugtracker.people.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

    final PersonRepository personRepository;

    public PersistenceConfig(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * https://springbootdev.com/2018/03/13/spring-data-jpa-auditing-with-createdby-createddate-lastmodifiedby-and-lastmodifieddate/
     *
     * @return Zalogowany użytkownik
     */
    @Bean
    AuditorAware<Person> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "";
            return Optional.ofNullable(personRepository.findLoggedUser(username));
        };
    }

}
