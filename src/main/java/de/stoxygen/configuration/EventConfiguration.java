package de.stoxygen.configuration;

import de.stoxygen.services.IndicatorService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {
    @Bean
    public Exchange eventExchange() {

        return new TopicExchange("STO2-Exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("aggregatorQueue");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange eventExchange) {
        return BindingBuilder.bind(queue).to(eventExchange).with("downloader.aggregate");
    }


    @Bean
    public IndicatorService indicatorService(AmqpTemplate rabbitTemplate, Exchange eventExchange) {
        return new IndicatorService(rabbitTemplate, eventExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
