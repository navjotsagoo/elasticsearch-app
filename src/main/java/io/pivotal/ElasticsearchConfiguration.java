package io.pivotal;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
@EnableElasticsearchRepositories(basePackages="io.pivotal")
public class ElasticsearchConfiguration {
	
	@Resource
	private Environment env;
	
	@Value("$(elasticsearch.host)")
	String hostname;
	
	@Value("$(elasticsearch.port")
	String port;
	
	@Bean
	public Client client() throws UnknownHostException {
	
		Settings settings = Settings.builder()
				.put("cluster.name", "elasticsearch")
				.put("client.transport.sniff", true).build();
		
		@SuppressWarnings("resource")
		TransportClient client = new PreBuiltTransportClient(settings).
				addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname),9300));
		
		return client;
	}
	
	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException{
		return new ElasticsearchTemplate(client());
	}
}
