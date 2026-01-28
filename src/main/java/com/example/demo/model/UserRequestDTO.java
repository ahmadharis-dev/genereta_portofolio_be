package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank
    @JsonProperty("Name")
    private String name;

    @NotBlank
    @JsonProperty("Fullname")
    private String fullname;

    @NotBlank
    @JsonProperty("Email")
    private String email;

    @NotBlank
    @JsonProperty("Password")
    private String password;

}
