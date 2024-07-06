package tech.run.msmagalu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.run.msmagalu.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
