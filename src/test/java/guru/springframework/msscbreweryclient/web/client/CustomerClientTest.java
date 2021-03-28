package guru.springframework.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.model.CustomerDto;

@SpringBootTest
class CustomerClientTest {
	
	@Autowired
	CustomerClient client;
	
	@Test
	void testGetCustomer() {
		CustomerDto dto = client.getCustomer(UUID.randomUUID());
		assertNotNull(dto);
	}

	@Test
	void testSaveNewCustomer() {
		CustomerDto dto = CustomerDto.builder().name("Alex").build();
		URI uri = client.saveNewCustomer(dto);
		System.out.println(uri);
		assertNotNull(uri);
	}

	@Test
	void testUpdateCustomerById() {
		CustomerDto dto = CustomerDto.builder().name("Alex").build();
		client.updateCustomerById(UUID.randomUUID(),dto);
	}

	@Test
	void testDeleteCustomer() {
		client.deleteCustomer(UUID.randomUUID());
	}

}
