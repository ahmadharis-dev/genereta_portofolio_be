package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "SessionId")
    private String sessionId;

    @Column(name = "ExpiryAt")
    private LocalDateTime expiryAt;

    @Column(name = "IsExpired")
    private Boolean IsExpired;

    @Column(name = "CreatedBy")
    private Integer createdBy;
}
