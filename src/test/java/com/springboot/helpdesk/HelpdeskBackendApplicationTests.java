package com.springboot.helpdesk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class HelpdeskBackendApplicationTests {

	@Test
	void contextLoads() {
        assertEquals(5,(2+3));
	}

}
