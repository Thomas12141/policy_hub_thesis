package org.example.policy_hub.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@SequenceGenerator(name = "jar_seq", sequenceName = "jar_sequence", initialValue = 1, allocationSize = 1)
public class JarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jar_seq")
    private Long id;
    private String name;
    private String filePath;
    private String description;
}