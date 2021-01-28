# kafka-intro
Understanding basic kafka skills on -- producer , consumer , topics, kafka cluser and configs

# kafka-intro
Understanding basic kafka skills on -- producer , consumer , topics, kafka cluser and configs

## Added Config for Producer

```
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

//flush
		producer.flush();

// close
		producer.close();
```

## added a callback and logger to get more info on sent message

```
	producer.send(producerRecord, new Callback() {

			public void onCompletion(RecordMetadata metadata, Exception exception) {

				callbackLog.info("timestamp -->" + metadata.timestamp());
				callbackLog.info("offset -->" + metadata.offset());
				callbackLog.info("partition -->" + metadata.partition());
				callbackLog.info("Topic  -->" + metadata.topic());
			}
		});
```



## 