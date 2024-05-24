package com.starrytasks.backend.rabbitmq;

import com.starrytasks.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationConsumer {
    @Value("${app.url}")
    private String appUrl;

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void receiveNotification(String message) {
        System.out.println("Received notification: " + message);


        String[] parts = message.split(", ");
        String taskName = parts[0].split(": ")[1];
        String childName = parts[1].split(": ")[1];
        String parentEmail = parts[2].split(": ")[1];

        String subject = String.format("Task Completed: %s", taskName);
        String body = String.format(
                "The task \"%s\" has been completed by %s.<br><br>To view more details, please <a href=\"%s/parent\">click here</a>.",
                taskName, childName, appUrl
        );

        emailService.sendSimpleMessage(parentEmail, subject, body);
    }
}
