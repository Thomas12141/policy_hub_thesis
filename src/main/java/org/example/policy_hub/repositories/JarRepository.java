package org.example.policy_hub.repositories;

import org.example.policy_hub.entities.JarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JarRepository extends JpaRepository<JarEntity, Long> {
    Optional<JarEntity> findByName(String moduleName);

}
