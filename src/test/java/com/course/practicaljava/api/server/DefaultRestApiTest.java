package com.course.practicaljava.api.server;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DefaultRestApiTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testWelcome() {
		webTestClient.get().uri("/api/welcome").exchange().expectStatus().is2xxSuccessful().expectBody(String.class)
				.value(v -> v.equalsIgnoreCase("welcome to spring boot"));
	}

//	@Test
	void testTime() {
		fail("Not yet implemented");
	}

//	@Test
	void testHeaderByAnnotation() {
		fail("Not yet implemented");
	}

//	@Test
	void testHeaderByRequest() {
		fail("Not yet implemented");
	}

}
