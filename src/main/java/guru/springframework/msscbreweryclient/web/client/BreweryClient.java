package guru.springframework.msscbreweryclient.web.client;

import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbreweryclient.web.model.BeerDto;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {

	public final String BEER_PATH_V1= "/api/v1/beer/"; 
	private String apiHost;

	private final RestTemplate restTemplate;
	
	public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public BeerDto getBeerById(UUID uuid) {
		return restTemplate.getForObject(apiHost + BEER_PATH_V1 +uuid.toString(), BeerDto.class);
				
	}
	
	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}
	
	
}
