package com.example.demo.controller;

import com.example.demo.model.ResumeRequestDTO;
import com.example.demo.model.ResumeResponseDTO;
import com.example.demo.service.ExperienceService;
import com.example.demo.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Operation(
            summary = "Download Resume"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResumeResponseDTO.class)
            )
    )
    @PostMapping
    public ResumeResponseDTO downloadResume(@RequestBody ResumeRequestDTO resumeRequestDTO) {
        return resumeService.downloadResume(resumeRequestDTO);
    }

}
