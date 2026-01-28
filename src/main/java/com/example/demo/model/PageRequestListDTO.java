package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestListDTO {
    @JsonProperty("PageNo")
    private Integer pageNo = 1;

    @JsonProperty("RecordPerPage")
    private Integer recordPerPage = 10;
}
