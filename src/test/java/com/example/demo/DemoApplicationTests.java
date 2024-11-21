package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")  // Activa el perfil 'test' para las pruebas
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
