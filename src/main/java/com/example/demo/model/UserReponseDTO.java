package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReponseDTO {
    @JsonProperty("Id")
    private int id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Fullname")
    private String fullname;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("Linkedin")
    private String linkedin;
    @JsonProperty("As")
    private String as;
}
