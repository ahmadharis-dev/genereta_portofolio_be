package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MasterTechResponseDTO {

    @JsonProperty("Id")
    private Integer id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Success")
    private Boolean success;
}
