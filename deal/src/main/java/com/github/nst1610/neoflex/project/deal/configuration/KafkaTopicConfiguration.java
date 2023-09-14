package com.github.nst1610.neoflex.project.deal.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {
    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private String kafkaServer;

    @Bean
    public KafkaAdmin admin()
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaServer);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic finishRegistration() {
        return new NewTopic("finish-registration", 1, (short) 1);
    }

    @Bean
    public NewTopic createDocuments() {
        return new NewTopic("create-documents", 1, (short) 1);
    }

    @Bean
    public NewTopic sendDocuments() {
        return new NewTopic("send-documents", 1, (short) 1);
    }

    @Bean
    public NewTopic sendSes() {
        return new NewTopic("send-ses", 1, (short) 1);
    }

    @Bean
    public NewTopic creditIssued() {
        return new NewTopic("credit-issued", 1, (short) 1);
    }

    @Bean
    public NewTopic applicationDenied() {
        return new NewTopic("application-denied", 1, (short) 1);
    }
}
