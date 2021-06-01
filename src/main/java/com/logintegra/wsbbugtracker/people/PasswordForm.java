package com.logintegra.wsbbugtracker.people;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PasswordForm {

    Long id;

    @NotBlank
    String password;

    @NotBlank
    String passwordAgain;

    boolean isValid;

    @AssertTrue(message = "passVerify field should be equal than pass field")
    public boolean isValid() {
        if (password == null)
            return false;
        if (passwordAgain == null)
            return false;
        else
            return this.password.equals(this.passwordAgain);
    }
}
