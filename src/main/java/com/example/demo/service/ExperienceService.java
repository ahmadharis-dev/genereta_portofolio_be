package com.example.demo.service;

import com.example.demo.model.ExperienceRequestDTO;
import com.example.demo.model.ExperienceResponseDTo;

public interface ExperienceService {
    ExperienceResponseDTo saveExperience(ExperienceRequestDTO requestDTO);

    ExperienceResponseDTo getExperienceByUserId(Integer userId);

    void insertOrUpdate(Integer id, Integer userId, String companyName,
                        String projectName, String descriptionTask,
                        String startDate, String endDate);
}