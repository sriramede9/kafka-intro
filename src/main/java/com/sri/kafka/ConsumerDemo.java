package com.sri.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemo {

	private static Logger callbackLog = LoggerFactory.getLogger(ConsumerDemo.class);

	public static void main(String[] args) {

		Properties properties = new Properties();

		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my_first_group");
		properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		// create consumer

		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

		// consume messages

		kafkaConsumer.subscribe(Arrays.asList("the_beginning"));

		while (true) {
			ConsumerRecords<String, String> poll = kafkaConsumer.poll(Duration.ofMillis(100));

			for (ConsumerRecord<String, String> record : poll) {

				callbackLog.info("Key \t" + record.key() + " \t value \t " + record.value());
				callbackLog.info("partition " + record.partition());
				callbackLog.info("Offset " + record.offset());
			}
		}

//		Properties props = new Properties();
//		props.put("bootstrap.servers", "localhost:9092");
//		props.put("group.id", "my_first_group");
//		props.put("enable.auto.commit", "true");
//		props.put("auto.commit.interval.ms", "1000");
//		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
//		consumer.subscribe(Arrays.asList("the_beginning"));
//		while (true) {
//			ConsumerRecords<String, String> records = consumer.poll(100);
//			for (ConsumerRecord<String, String> record : records)
//				System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//		}

	}
}
