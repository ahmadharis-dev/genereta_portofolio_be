package com.example.demo.service;

import com.example.demo.model.ExperienceDeleteResponseDTO;
import com.example.demo.model.ExperienceRequestDTO;
import com.example.demo.model.ExperienceResponseDTo;
import com.example.demo.model.UpdateOrderExperienceRequestDTO;

public interface ExperienceService {
    ExperienceResponseDTo saveExperience(ExperienceRequestDTO requestDTO);

    ExperienceResponseDTo getExperienceByUserId(Integer userId);

    Integer getLastOrderByUserId(Integer userId);

    void insertOrUpdate(Integer id, Integer userId, String companyName,
                        String projectName, String descriptionTask,
                        String startDate, String endDate);

    ExperienceDeleteResponseDTO deleteById(Integer id);

    ExperienceResponseDTo updateOrder(UpdateOrderExperienceRequestDTO request);
}