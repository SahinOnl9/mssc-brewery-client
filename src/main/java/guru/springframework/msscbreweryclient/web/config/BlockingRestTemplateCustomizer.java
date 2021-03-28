package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

	private final Integer maxTotalConnection;
	private final Integer defaultMaxTotalConnections;
	private final Integer connectionRequestTimeout;
	private final Integer socketTimeout;

	public BlockingRestTemplateCustomizer(@Value("${sfg.maxTotalConnection}") Integer maxTotalConnection,
			@Value("${sfg.defaultMaxTotalConnections}") Integer defaultMaxTotalConnections, 
			@Value("${sfg.connectionRequestTimeout}") Integer connectionRequestTimeout,
			@Value("${sfg.socketTimeout}") Integer socketTimeout) {
		super();
		this.maxTotalConnection = maxTotalConnection;
		this.defaultMaxTotalConnections = defaultMaxTotalConnections;
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.socketTimeout = socketTimeout;
	}

	public ClientHttpRequestFactory clientHttpRequestFactory() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxTotalConnection);
		connectionManager.setDefaultMaxPerRoute(defaultMaxTotalConnections);

		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout)
				.build();

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).setDefaultRequestConfig(requestConfig)
				.build();

		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Override
	public void customize(RestTemplate restTemplate) {
		restTemplate.setRequestFactory(this.clientHttpRequestFactory());
	}
}