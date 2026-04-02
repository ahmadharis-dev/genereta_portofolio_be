package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseDTO {
    @JsonProperty("Id")
    private int id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Fullname")
    private String fullname;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("Linkedin")
    private String linkedin;
    @JsonProperty("As")
    private String as;

    @JsonProperty("email")
    private String email;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("SessionId")
    private String sessionId;
}
