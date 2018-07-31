package de.stoxygen.services;


import de.stoxygen.model.IndicatorCalculateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IndicatorService {
    private static final Logger logger = LoggerFactory.getLogger(IndicatorService.class);
    private final AmqpTemplate rabbitTemplate;
    private final Exchange exchange;

    public IndicatorService(AmqpTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void createCalculate(String msg_exchange, String msg_bond, String msg_aggregate, Date msg_timestamp) {
        String routingKey = "indicator.calculate";
        final IndicatorCalculateMessage message = new IndicatorCalculateMessage(msg_exchange, msg_bond, msg_aggregate, msg_timestamp);
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, message);
    }
}
