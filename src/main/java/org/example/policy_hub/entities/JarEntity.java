package org.example.policy_hub.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "jar_seq", sequenceName = "jar_sequence", allocationSize = 1)
public class JarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jar_seq")
    private Long id;
    private String name;
    private String filePath;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JarEntity jarEntity = (JarEntity) o;
        return Objects.equals(id, jarEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}