package fr.fs.rqp.repository;

import fr.fs.rqp.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}

