package com.sri.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {

	public static void main(String[] args) {

		// create producer properties

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// create the producer

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		// send data

		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("the_beginning",
				"Hello From Java !!!!");

		producer.send(producerRecord);

		// this is async and you have to flush and close once you send the record

		producer.flush();

		producer.close();

//		System.out.println("Yo Mama");
	}
}
