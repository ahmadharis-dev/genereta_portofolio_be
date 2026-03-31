package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceResponseDTo {
    @JsonProperty("UserId")
    private Integer userId;

    @JsonProperty("List")
    private List<ExperienceResponseDTo.ExperienceItemResponse> list;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExperienceItemResponse{
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
    }
}
