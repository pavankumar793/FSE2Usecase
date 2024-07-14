// package com.fse2.usecase.userservice.config;

// import org.apache.kafka.clients.admin.NewTopic;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.config.TopicBuilder;

// @Configuration
// public class KafkaTopicConfig {
//     @Bean
//     public NewTopic blogSiteApplicationTopic() {
//         return TopicBuilder.name("blogSiteApplicationTopic")
//                 .build();
//     }
// }

// <configuration>
//     <appender name="KAFKA" class="com.github.danielwegener.logback.kafka.KafkaAppender">
//         <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
//             <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
//         </encoder>
//         <topic>blogSiteApplicationTopic</topic>
//         <keyingStrategy class="com.github.danielwegener.logback.kafka.keying.NoKeyKeyingStrategy"/>
//         <deliveryStrategy class="com.github.danielwegener.logback.kafka.delivery.AsynchronousDeliveryStrategy"/>
//         <producerConfig>bootstrap.servers=localhost:9092</producerConfig>
//     </appender>

//     <root level="INFO">
//         <appender-ref ref="KAFKA" />
//     </root>
// </configuration>