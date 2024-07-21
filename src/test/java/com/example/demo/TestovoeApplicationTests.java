package com.example.demo;

import com.example.demo.repository.FileRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.config.location=classpath:application-test.properties"})
class TestovoeApplicationTests {

	@Test
	void contextLoads() {
	}

}
