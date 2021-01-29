package com.sri.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerWithKeys {

	private static Logger callbackLog = LoggerFactory.getLogger(ProducerWithKeys.class);

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// create producer properties

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// create the producer

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		// send data

		for (int i = 0; i < 10; i++) {

			String key = "id_" + Integer.toString(i);

			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("the_beginning", key,
					"Hello with " + i);

			callbackLog.info(key);

			// 0 -1
			// 1 -> 0
			// 2-2
			// 3 -> 0
			// 4 - 2

			producer.send(producerRecord, new Callback() {

				public void onCompletion(RecordMetadata metadata, Exception exception) {

					callbackLog.info("timestamp -->" + metadata.timestamp());
					callbackLog.info("offset -->" + metadata.offset());
					callbackLog.info("partition -->" + metadata.partition());
					callbackLog.info("Topic  -->" + metadata.topic());
				}
			}).get();
		}

		// this is async and you have to flush and close once you send the record

		producer.flush();

		producer.close();

//		System.out.println("Yo Mama");
	}
}
