package org.balaguta.currconv.web;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ToString
@ValidUserDto
public class UserDto {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String repeatPassword;
    @Birthday
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    @NotNull
    private Address address;

    @Data
    public static class Address {
        @NotEmpty
        private String line1;
        private String line2;
        @NotEmpty
        private String city;
        @NotEmpty
        private String zip;
        private Long country;
    }
}
