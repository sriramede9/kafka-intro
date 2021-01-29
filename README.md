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



##  consumer config

```
	Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "my_first_group");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList("the_beginning"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records)
				System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
		}

```

### Consumer Groups

# If we have more than one consumer-group running of same group, then loadbalancing among the partitions is automatically done 