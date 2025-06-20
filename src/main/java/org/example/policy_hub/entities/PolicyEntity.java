package org.example.policy_hub.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String uid;

    @Column(columnDefinition = "TEXT")
    private String jsonContent;

    private LocalDateTime uploadedAt;
}
