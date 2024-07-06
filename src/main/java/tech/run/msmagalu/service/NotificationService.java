package tech.run.msmagalu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.run.msmagalu.controller.dto.ScheduleNotificationDto;
import tech.run.msmagalu.entity.Channel;
import tech.run.msmagalu.entity.Notification;
import tech.run.msmagalu.entity.Status;
import tech.run.msmagalu.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(NotificationRepository notificationRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    public void scheduleNotification(ScheduleNotificationDto dto) {
        notificationRepository.save(dto.toNotification());
    }

    public Optional<Notification> getById(Long notificationId) {
        return notificationRepository.findById(notificationId);
    }

    public void cancelNotification(Long notificationId) {
        var notification = getById(notificationId);

        if (notification.isPresent()) {
            notification.get().setStatus(Status.Values.CANCELED.toStatus());
            notificationRepository.save(notification.get());
        }
    }

    public void checkAndSend(LocalDateTime dateTime) {
        var notifications = notificationRepository.findByStatusInAndDateTimeBefore(
                List.of(Status.Values.PENDING.toStatus(),Status.Values.ERROR.toStatus()),
                dateTime
        );

        notifications.forEach(sendNotification());
    }

    private Consumer<Notification> sendNotification() {
        return n -> {
            try {
                sendMessage(n);

                n.setStatus(Status.Values.SUCCESS.toStatus());
            } catch (Exception e) {
                n.setStatus(Status.Values.ERROR.toStatus());
            }
            notificationRepository.save(n);
        };
    }

    private void sendMessage(Notification n) {
        if(Objects.equals(n.getChannel().getDescription(), Channel.Values.EMAIL.toChannel().getDescription())) {
            sendMail(n);
        } else if(Objects.equals(n.getChannel().getDescription(),Channel.Values.WHATSAPP.toChannel().getDescription())) {
            sendWhatsapp(n);
        } else if(Objects.equals(n.getChannel().getDescription(),Channel.Values.SMS.toChannel().getDescription())) {
            sendSms(n);
        } else if(Objects.equals(n.getChannel().getDescription(),Channel.Values.PUSH.toChannel().getDescription())) {
            sendPush(n);
        }
    }

    private void sendPush(Notification n) {
        //TODO - Envio de notificação por PUSH
        System.out.println(n.getDestination() + n.getMessage());
    }

    private void sendSms(Notification n) {
        //TODO - Envio de notificação por SMS
        System.out.println(n.getMessage() + n.getMessage());
    }

    private void sendWhatsapp(Notification n) {
        //TODO - Envio de notificação por WHATSAPP
        System.out.println(n.getMessage() + n.getMessage());
    }

    private void sendMail(Notification n) {
        var message = new SimpleMailMessage();
        message.setFrom("from@email.com");
        message.setTo(n.getDestination());
        message.setSubject("Spring");
        message.setText(n.getMessage());
        mailSender.send(message);
        logger.info("Email sent");
    }
}
