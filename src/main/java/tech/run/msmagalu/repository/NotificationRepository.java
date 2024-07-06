package tech.run.msmagalu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.run.msmagalu.entity.Notification;
import tech.run.msmagalu.entity.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatusInAndDateTimeBefore(List<Status> status, LocalDateTime dateTime);
}
