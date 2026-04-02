package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExperienceDeleteResponseDTO {
    @JsonProperty("Message")
    private String message;
}
