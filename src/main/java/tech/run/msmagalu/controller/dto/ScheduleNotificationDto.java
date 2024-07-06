package tech.run.msmagalu.controller.dto;

import tech.run.msmagalu.entity.Channel;
import tech.run.msmagalu.entity.Notification;
import tech.run.msmagalu.entity.Status;

import java.time.LocalDateTime;

public record ScheduleNotificationDto(LocalDateTime dateTime,
                                      String destination,
                                      String message,
                                      Channel.Values channel) {

    public Notification toNotification() {
        return new Notification(
                dateTime,
                destination,
                message,
                channel.toChannel(),
                Status.Values.PENDING.toStatus());
    }
}
