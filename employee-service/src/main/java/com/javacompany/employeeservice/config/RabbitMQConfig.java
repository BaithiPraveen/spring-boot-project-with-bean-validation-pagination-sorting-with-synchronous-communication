//package com.javacompany.employeeservice.config;
//
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//@Configuration
//public class RabbitMQConfig {
//
//    @Value("${rabbitmq.department.queue}")
//    private String departmentRequestQueueName;
//
//    @Value("${rabbitmq.department.exchange}")
//    private String departmentExchangeName;
//
//    @Value("${rabbitmq.department.routingkey}")
//    private String departmentRoutingKey;
//
//    @Bean
//    public Queue departmentRequestQueue() {
//        return new Queue(departmentRequestQueueName);
//    }
//
//    @Bean
//    public DirectExchange departmentExchange() {
//        return new DirectExchange(departmentExchangeName);
//    }
//
//    @Bean
//    public Binding binding(Queue departmentRequestQueue, DirectExchange departmentExchange) {
//        return BindingBuilder.bind(departmentRequestQueue).to(departmentExchange).with(departmentRoutingKey);
//    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public AmqpTemplate template(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
//}
