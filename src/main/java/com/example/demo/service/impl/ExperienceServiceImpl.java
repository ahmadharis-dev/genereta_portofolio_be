package com.example.demo.service.impl;

import com.example.demo.entity.Experience;
import com.example.demo.entity.User;
import com.example.demo.model.ExperienceRequestDTO;
import com.example.demo.model.ExperienceResponseDTo;
import com.example.demo.model.UpdateOrderExperienceRequestDTO;
import com.example.demo.repository.ExperienceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository,
                                 UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Integer getLastOrderByUserId(Integer userId) {
        Integer order = experienceRepository.getLastOrderByUserId(userId);
        if (order == null) {
            order = 0;
        }
        return order + 1;
    }

    @Override
    @Transactional
    public ExperienceResponseDTo saveExperience(ExperienceRequestDTO requestDTO) {
        List<ExperienceRequestDTO.ExperienceItemRequest> experiences = requestDTO.getList();
        Integer userId = requestDTO.getUserId();

        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        // FIXED: Gunakan List untuk ExperienceItemResponse, bukan ExperienceItemRequest
        List<ExperienceResponseDTo.ExperienceItemResponse> savedExperiences = new ArrayList<>();

        for (ExperienceRequestDTO.ExperienceItemRequest experience : experiences) {
            // Save ke database dan dapatkan ID
            Experience savedExperience = insertAndReturn(experience, userId);

            // Konversi ke Response DTO
            ExperienceResponseDTo.ExperienceItemResponse responseItem =
                    new ExperienceResponseDTo.ExperienceItemResponse();
            responseItem.setId(savedExperience.getId());
            responseItem.setCompanyName(savedExperience.getCompanyName());
            responseItem.setProjectName(savedExperience.getProjectName());
            responseItem.setDescriptionTask(savedExperience.getDescriptionTask());
            responseItem.setStartDate(savedExperience.getStartDate());
            responseItem.setEndDate(savedExperience.getEndDate());
            responseItem.setOrder(savedExperience.getOrder());

            savedExperiences.add(responseItem);
        }

        ExperienceResponseDTo response = new ExperienceResponseDTo();
        response.setUserId(requestDTO.getUserId());
        response.setList(savedExperiences); // Sekarang tipenya match: List<ExperienceItemResponse>

        log.info("Successfully saved {} experiences for user ID: {}", savedExperiences.size(), userId);

        return response;
    }

    // Helper method untuk save dan return entity
    private Experience insertAndReturn(ExperienceRequestDTO.ExperienceItemRequest request, Integer userId) {
        Integer order = this.getLastOrderByUserId(userId);
        Integer experienceId =  request.getId();
        Experience experience;
        if(experienceId == null || experienceId == 0) {
            experience = new Experience();
        }else{
            experience = experienceRepository.getById(experienceId);
        }

        experience.setUserId(userId);
        experience.setCompanyName(request.getCompanyName());
        experience.setProjectName(request.getProjectName());
        experience.setDescriptionTask(request.getDescriptionTask());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setOrder(order);
        return experienceRepository.save(experience);
    }
    @Override
    public ExperienceResponseDTo getExperienceByUserId(Integer userId) {
        List<Experience> experiences = experienceRepository.getByUser(userId);
        Integer size = experiences.size();
        log.info("Size of experience is {}", size);

        if (size == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found for user id: " + userId);
        }

        List<ExperienceResponseDTo.ExperienceItemResponse> items = experiences.stream()
                .map(exp -> {
                    ExperienceResponseDTo.ExperienceItemResponse dto =
                            new ExperienceResponseDTo.ExperienceItemResponse();
                    dto.setOrder(exp.getOrder());
                    dto.setId(exp.getId());
                    dto.setCompanyName(exp.getCompanyName());
                    dto.setProjectName(exp.getProjectName());
                    dto.setDescriptionTask(exp.getDescriptionTask());
                    dto.setStartDate(exp.getStartDate());
                    dto.setEndDate(exp.getEndDate());
                    return dto;
                })
                .toList();

        ExperienceResponseDTo response = new ExperienceResponseDTo();
        response.setUserId(userId);
        response.setList(items);

        return response;
    }

    @Override
    @Transactional
    public void insertOrUpdate(Integer id, Integer userId, String companyName,
                               String projectName, String descriptionTask,
                               String startDate, String endDate) {
        if (id == null || id == 0) {
            // Create new experience
            Experience experience = new Experience();
            experience.setUserId(userId);
            experience.setCompanyName(companyName);
            experience.setProjectName(projectName);
            experience.setDescriptionTask(descriptionTask);
            experience.setStartDate(startDate);
            experience.setEndDate(endDate);
            experienceRepository.save(experience);
            log.debug("Created new experience for user ID: {}", userId);
        } else {
            // Update existing experience
            Experience experience = experienceRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Experience not found with id: " + id
                    ));
            experience.setCompanyName(companyName);
            experience.setProjectName(projectName);
            experience.setDescriptionTask(descriptionTask);
            experience.setStartDate(startDate);
            experience.setEndDate(endDate);
            experienceRepository.save(experience);
            log.debug("Updated experience with ID: {}", id);
        }
    }

    @Override
    @Transactional
    public ExperienceResponseDTo updateOrder(UpdateOrderExperienceRequestDTO request) {
        List<Integer> ids = request.getExperienceIds();

        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            experienceRepository.updateSortOrder(id, i + 1);
        }

        return getExperienceByUserId(request.getUserId());
    }
}