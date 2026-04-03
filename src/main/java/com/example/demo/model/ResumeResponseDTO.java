package com.example.demo.model;

import com.example.demo.entity.Experience;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ResumeResponseDTO {
    @JsonProperty("Fullname")
    private String fullname;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("AsRole")
    private String asRole;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Linkedin")
    private String linkedin;

    @JsonProperty("ExperienceList")
    private List<Experience> experienceList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Experience {
        @JsonProperty("Id")
        private Integer id;

        @JsonProperty("Order")
        private Integer order;

        @JsonProperty("CompanyName")
        private String companyName;

        @JsonProperty("ProjectName")
        private String projectName;

        @JsonProperty("DescriptionTask")
        private String descriptionTask;

        @JsonProperty("StartDate")
        private String startDate;

        @JsonProperty("EndDate")
        private String endDate;

        @JsonProperty("As")
        private String as;
    }

}
