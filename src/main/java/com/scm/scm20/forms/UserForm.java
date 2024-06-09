package com.scm.scm20.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is Required")
    @Size(min = 3,message = "Min 3 character is required")
    private String name;
    @Email(message = "Invail Email Address")
    @NotBlank(message = "Email is required")    
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min 6 charaxter is Requried")
    private String password;
    @NotBlank(message = "About is Required")
    private String about;
    @Size(max = 10, message = "Invalid Phone Number")
    private String phoneNumber;

}
