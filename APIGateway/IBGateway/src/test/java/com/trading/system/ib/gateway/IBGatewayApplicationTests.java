package com.trading.system.ib.gateway;

import com.trading.system.ib.gateway.config.TestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class IBGatewayApplicationTests {

	@Test
	void contextLoads() {

	}

}
