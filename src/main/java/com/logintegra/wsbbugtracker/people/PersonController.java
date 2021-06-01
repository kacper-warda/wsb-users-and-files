package com.logintegra.wsbbugtracker.people;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class PersonController {
    final PersonService personService;
    final PersonRepository personRepository;
    private final Logger log = LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @GetMapping
    public String showUserList(Model model) {
        System.out.println("index");
        model.addAttribute("users", personRepository.findAllByEnabled(true));
        return "user/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("personForm", new PersonForm(person));
        return "user/show";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid PersonForm personForm,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            personForm.setId(id);
            model.addAttribute("personForm", personForm);
            return "user/show";
        }

        personService.saveUser(personForm);
        System.out.println("saved");
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        personService.disable(person);
        return "redirect:/user";
    }

    @PostMapping("/add-user")
    public String addUser(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("person", person);
            return "user/create";
        }

        personRepository.save(person);
        return "redirect:/user";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("person", new Person());
        return "user/create";
    }


    @GetMapping("/editPassword/{id}")
    public String showUpdatePassForm(@PathVariable("id") long id, Model model) {
        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setId(id);
        model.addAttribute("passwordForm", passwordForm);
        return "user/password";
    }

    @PostMapping("/updatePassword/{id}")
    public String updatePassword(@PathVariable("id") long id, @Valid PasswordForm passwordForm,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            passwordForm.setId(id);
            model.addAttribute("passwordForm", passwordForm);
            return "user/password";
        }

        personService.updatePassword(passwordForm);
        System.out.println("saved");
        return "redirect:/user";
    }
}
