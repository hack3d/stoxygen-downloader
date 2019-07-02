package de.stoxygen.services;

import de.stoxygen.model.aggregate.AggregateMessage;
import de.stoxygen.model.aggregate.AggregateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class AggregatorService {
    private static final Logger logger = LoggerFactory.getLogger(AggregatorService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final Exchange amqpExchange;


    public AggregatorService(RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpExchange = exchange;
    }

    /**
     * {"exchange":"btsp","bond":"XFC000000001","aggregateType":"ONE_MINUTE","insertTimestamp":1561990388462}
     * @param exchange
     * @param bond
     * @param aggregateType
     * @param insertTimestamp
     */
    public void createAggregateMessage(String exchange, String bond, AggregateType aggregateType, Date insertTimestamp) {
        String routingKey = "downloader.aggregate";
        final AggregateMessage aggregateMessage = new AggregateMessage();
        aggregateMessage.setExchange(exchange);
        aggregateMessage.setBond(bond);
        aggregateMessage.setAggregateType(aggregateType);
        aggregateMessage.setInsertTimestamp(insertTimestamp);
        rabbitTemplate.convertAndSend(amqpExchange.getName(), routingKey, aggregateMessage);
    }
}
