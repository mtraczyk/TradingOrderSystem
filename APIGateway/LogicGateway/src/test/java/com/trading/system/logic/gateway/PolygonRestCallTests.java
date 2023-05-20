package com.trading.system.logic.gateway;

import com.trading.system.logic.gateway.rest.PolygonRestCall;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class PolygonRestCallTests {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PolygonRestCall polygonRestCall;

	@Test
	public void contextLoads() {
		assertThat(restTemplate).isNotNull();
		assertThat(polygonRestCall).isNotNull();
	}

	@Test
	public void testCall() {
		var response = polygonRestCall.getAggregateBars("AAPL", "1", "day", "2023-01-01", "2023-01-06");
		assertThat(response).isNotNull();

		System.out.println(response);
	}

}
