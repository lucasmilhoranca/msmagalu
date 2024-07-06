package tech.run.msmagalu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.run.msmagalu.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
