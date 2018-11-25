package de.stoxygen.services;

import de.stoxygen.model.notification.NotificationCategory;
import de.stoxygen.model.notification.NotificationMessage;
import de.stoxygen.model.notification.NotificationSeverity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final AmqpTemplate rabbitTemplate;
    private final Exchange exchange;

    public NotificationService(AmqpTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    /**
     * Sample message: {"severity": "CRITICAL", "sender": "sto2-indicator", "category": "USER", "content": "Test notification", "username": "admin"}
     */
    public void createNotification(NotificationSeverity notificationSeverity, String sender,
                                   NotificationCategory notificationCategory, String content) {
        this.createNotification(notificationSeverity, sender, notificationCategory, content, "admin");
    }

    public void createNotification(NotificationSeverity notificationSeverity, String sender,
                                   NotificationCategory notificationCategory, String content, String username) {
        String routingKey = "notification.new";
        final NotificationMessage notificationMessage = new NotificationMessage(notificationSeverity.toString(), sender,
                notificationCategory.toString(), content, username);
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, notificationMessage);
    }
}
