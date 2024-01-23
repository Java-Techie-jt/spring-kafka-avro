package com.javatechie.producer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.javatechie.dto.Employee;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {KafkaAvroProducer.class})
@ExtendWith(SpringExtension.class)
class KafkaAvroProducerTest {
    @Autowired
    private KafkaAvroProducer kafkaAvroProducer;

    @MockBean
    private KafkaTemplate<String, Employee> kafkaTemplate;

    /**
     * Method under test: {@link KafkaAvroProducer#send(Employee)}
     */
    @Test
    void testSend() {
        // Arrange
        when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any()))
                .thenReturn(new CompletableFuture<>());

        // Act
        kafkaAvroProducer.send(new Employee());

        // Assert that nothing has changed
        verify(kafkaTemplate).send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any());
    }

    /**
     * Method under test: {@link KafkaAvroProducer#send(Employee)}
     */
    @Test
    void testSend2() {
        // Arrange
        CompletableFuture<SendResult<String, Employee>> completableFuture = new CompletableFuture<>();
        ProducerRecord<String, Employee> producerRecord = new ProducerRecord<>("Topic", new Employee());

        completableFuture.obtrudeValue(
                new SendResult<>(producerRecord, new RecordMetadata(new TopicPartition("Topic", 1), 1L, 1, 10L, 3, 3)));
        when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any()))
                .thenReturn(completableFuture);

        // Act
        kafkaAvroProducer.send(new Employee());

        // Assert
        verify(kafkaTemplate).send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any());
    }

    /**
     * Method under test: {@link KafkaAvroProducer#send(Employee)}
     */
    @Test
    void testSend3() {
        // Arrange
        CompletableFuture<SendResult<String, Employee>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeException(new Throwable());
        when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any()))
                .thenReturn(completableFuture);

        // Act
        kafkaAvroProducer.send(new Employee());

        // Assert
        verify(kafkaTemplate).send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any());
    }

    /**
     * Method under test: {@link KafkaAvroProducer#send(Employee)}
     */
    @Test
    void testSend4() {
        // Arrange
        CompletableFuture<SendResult<String, Employee>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeValue(new SendResult<>(new ProducerRecord<>("Topic", new Employee()), null));
        when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any()))
                .thenReturn(completableFuture);

        // Act
        kafkaAvroProducer.send(new Employee());

        // Assert
        verify(kafkaTemplate).send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any());
    }

    /**
     * Method under test: {@link KafkaAvroProducer#send(Employee)}
     */
    @Test
    void testSend5() {
        // Arrange
        CompletableFuture<SendResult<String, Employee>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeValue(null);
        when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any()))
                .thenReturn(completableFuture);

        // Act
        kafkaAvroProducer.send(new Employee());

        // Assert
        verify(kafkaTemplate).send(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Employee>any());
    }
}
