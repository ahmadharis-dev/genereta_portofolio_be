package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class UpdateOrderExperienceRequestDTO {
    private Integer userId;
    private List<Integer> experienceIds;
}
