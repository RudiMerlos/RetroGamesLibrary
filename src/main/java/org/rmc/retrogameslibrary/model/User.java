package org.rmc.retrogameslibrary.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String firstName;

    private String lastName;

    private LocalDate birthdate;

    private String email;

    private String password;
}
