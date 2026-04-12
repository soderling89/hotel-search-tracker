package com.emanuel.sodero.hotel_search_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"hotel_availability_searches"})
@ActiveProfiles("test")
class HotelSearchTrackerApplicationTests {

	@Test
	void contextLoads() {
	}

}
