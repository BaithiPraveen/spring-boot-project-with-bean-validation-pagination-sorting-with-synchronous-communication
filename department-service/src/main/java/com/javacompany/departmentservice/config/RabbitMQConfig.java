//package com.javacompany.departmentservice.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//    @Value("${department.request}")
//    private String departmentRequest;
//
//    @Value("${department.response}")
//    private String departmentResponse;
//
//    @Value("${department.exchange}")
//    private String departmentExchangeName;
//
//    @Bean
//    public Queue departmentRequestQueue() {
//        return new Queue(departmentRequest);
//    }
//
//    @Bean
//    public Queue departmentResponseQueue() {
//        return new Queue(departmentResponse);
//    }
//
//    @Bean
//    public DirectExchange departmentExchange() {
//        return new DirectExchange(departmentExchangeName);
//    }
//
//    @Bean
//    public Binding bindingRequest(Queue departmentRequestQueue, DirectExchange departmentExchange) {
//        return BindingBuilder.bind(departmentRequestQueue).to(departmentExchange).with(departmentRequest);
//    }
//
//    @Bean
//    public Binding bindingResponse(Queue departmentResponseQueue, DirectExchange departmentExchange) {
//        return BindingBuilder.bind(departmentResponseQueue).to(departmentExchange).with(departmentResponse);
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
//
