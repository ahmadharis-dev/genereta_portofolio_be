package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MasterTechRequestDTO {
    @NotBlank
    @JsonProperty("Name")
    private String name;
}
