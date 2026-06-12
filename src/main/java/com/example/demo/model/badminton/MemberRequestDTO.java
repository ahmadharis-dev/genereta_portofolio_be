package com.example.demo.model.badminton;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberRequestDTO {
    @NotBlank
    @JsonProperty("Name")
    private String name;

    @NotBlank
    @JsonProperty("Classname")
    private String classname;

    @NotBlank
    @JsonProperty("Classes")
    private String classes;
}
