package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "`order`")
    private Integer order;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "projectName")
    private String projectName;

    @Lob
    @Column(name = "descriptionTask", columnDefinition = "LONGTEXT")
    private String descriptionTask;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "asRole")
    private String as_role;


}
