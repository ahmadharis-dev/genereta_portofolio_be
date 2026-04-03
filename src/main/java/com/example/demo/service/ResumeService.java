package com.example.demo.service;

import com.example.demo.entity.Experience;
import com.example.demo.entity.User;
import com.example.demo.model.ResumeRequestDTO;
import com.example.demo.model.ResumeResponseDTO;
import com.example.demo.repository.ExperienceRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeService {
    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    public ResumeService(ExperienceRepository experienceRepository,
                                 UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }

    public ResumeResponseDTO downloadResume(ResumeRequestDTO requestDTO) {
        Integer id = requestDTO.getId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        List<com.example.demo.entity.Experience> experiencesFromDb = experienceRepository.getByUser(id);

        ResumeResponseDTO response = new ResumeResponseDTO();
        response.setFullname(user.getFullname());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setAsRole(user.getAs_role());
        response.setLinkedin(user.getLinkedin());
        List<ResumeResponseDTO.Experience> experienceDtoList = new ArrayList<>();

        if (experiencesFromDb != null) {
            for (com.example.demo.entity.Experience entity : experiencesFromDb) {
                ResumeResponseDTO.Experience expDto = new ResumeResponseDTO.Experience();
                expDto.setId(entity.getId());
                expDto.setCompanyName(entity.getCompanyName());
                expDto.setProjectName(entity.getProjectName());
                expDto.setDescriptionTask(entity.getDescriptionTask());
                expDto.setStartDate(entity.getStartDate());
                expDto.setEndDate(entity.getEndDate());
                expDto.setAs(entity.getAs_role());

                experienceDtoList.add(expDto);
            }
        }

        response.setExperienceList(experienceDtoList);

        return response;
    }
}
