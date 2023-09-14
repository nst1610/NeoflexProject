package com.github.nst1610.neoflex.project.dossier.configuration;

import com.github.nst1610.neoflex.project.dossier.model.EmailMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "dossier");
        props.put(JsonDeserializer.TYPE_MAPPINGS, "EmailMessage:com.github.nst1610.neoflex.project.dossier.model.EmailMessage");
        return props;
    }

    @Bean
    public ConsumerFactory<String, EmailMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new StringDeserializer(), new JsonDeserializer<>(EmailMessage.class, true));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, EmailMessage>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
