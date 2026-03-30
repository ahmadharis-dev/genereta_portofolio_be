package com.example.demo.controller;

import com.example.demo.model.ExperienceRequestDTO;
import com.example.demo.model.ExperienceResponseDTo;
import com.example.demo.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @Operation(
            summary = "Create Experience",
            description = "Experience has been created"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Experience has been created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExperienceResponseDTo.class)
            )
    )
    @PostMapping
    public ExperienceResponseDTo save(@RequestBody ExperienceRequestDTO requestDTO) {
        log.info("Received request to save experiences for user ID: {}", requestDTO.getUserId());
        return experienceService.saveExperience(requestDTO);
    }

    @Operation(summary = "Get User Experience")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExperienceResponseDTo.class)
            )
    )
    @GetMapping("/{userId}")
    public ExperienceResponseDTo getByUser(@PathVariable Integer userId) {
        log.info("Received request to get experiences for user ID: {}", userId);
        return experienceService.getExperienceByUserId(userId);
    }
}