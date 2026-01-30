package com.example.demo.controller;

import com.example.demo.entity.Experience;
import com.example.demo.entity.User;
import com.example.demo.model.ExperienceRequestDTO;
import com.example.demo.model.ExperienceResponseDTo;
import com.example.demo.repository.ExperienceRepository;
import com.example.demo.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/experience")
public class ExperienceController {
    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;
    public ExperienceController(ExperienceRepository experienceRepository,UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }
    @Operation(
            summary = "Create Experience",
            description = "Experience has been created"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Experienct has been created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExperienceResponseDTo.class)
            )
    )
    @PostMapping
    public Object save(@RequestBody ExperienceRequestDTO requestDTO){
        List<ExperienceRequestDTO.ExperienceItemRequest> experiences = requestDTO.getList();
        Integer userId = requestDTO.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List mapping = new ArrayList<>();
        experiences.forEach(experience -> {
            String companyName = experience.getCompanyName();
            String projectName = experience.getProjectName();
            String descriptionTask = experience.getDescriptionTask();
            String startDate = experience.getStartDate();
            String endDate = experience.getEndDate();
            insertOrUpdate(0, userId,companyName,projectName,descriptionTask,startDate,endDate);
            mapping.add(experience);
        });
        ExperienceResponseDTo response = new ExperienceResponseDTo();
        response.setUserId(requestDTO.getUserId());
        response.setList(mapping);
        return response;

    }
    @Operation(
            summary = "User Experience"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExperienceResponseDTo.class)
            )
    )
    @GetMapping("/{userId}")
    public Object GetByUser(@PathVariable Integer userId){
        List<Experience> experiences = experienceRepository.getByUser(userId);
        Integer size = experiences.size();
        log.info("Size of experience is " + size);
        ExperienceResponseDTo response = new ExperienceResponseDTo();
        if(size == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }else{
            List<ExperienceResponseDTo.ExperienceItemResponse> items =
                    experiences.stream().map(exp -> {
                        ExperienceResponseDTo.ExperienceItemResponse dto =
                                new ExperienceResponseDTo.ExperienceItemResponse();

                        dto.setId(exp.getId());
                        dto.setCompanyName(exp.getCompanyName());
                        dto.setProjectName(exp.getProjectName());
                        dto.setDescriptionTask(exp.getDescriptionTask());
                        dto.setStartDate(exp.getStartDate());
                        dto.setEndDate(exp.getEndDate());

                        return dto;
                    }).toList();

            response.setUserId(userId);
            response.setList(items);
        }
        return response;
    }

    public void insertOrUpdate(Integer id, Integer userId, String companyName, String projectName, String descriptionTask, String StartDate, String EndDate){
        if(id == null || id == 0){
            Experience experience = new Experience();
            experience.setUserId(userId);
            experience.setCompanyName(companyName);
            experience.setProjectName(projectName);
            experience.setDescriptionTask(descriptionTask);
            experience.setStartDate(StartDate);
            experience.setEndDate(EndDate);
            experienceRepository.save(experience);
        }else{
            Experience experience = experienceRepository.findById(id).orElse(null);
            experience.setCompanyName(companyName);
            experience.setProjectName(projectName);
            experience.setDescriptionTask(descriptionTask);
            experience.setStartDate(StartDate);
            experience.setEndDate(EndDate);
            experienceRepository.save(experience);
        }
    }

}
