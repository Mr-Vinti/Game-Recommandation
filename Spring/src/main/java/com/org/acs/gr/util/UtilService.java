package com.org.acs.gr.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import com.org.acs.gr.bean.GamespotResponse;
import com.org.acs.gr.config.ApplicationProperties;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UtilService {
	private static Consumer<ClientCodecConfigurer> consumer = configurer -> {
		final ClientCodecConfigurer.ClientDefaultCodecs codecs = configurer.defaultCodecs();
		codecs.maxInMemorySize(20 * 1024 * 1024);
	};

	static Map<String, String> defaultUriVariables = new HashMap<>() {
		private static final long serialVersionUID = -3110754644454897702L;
		{
			put("api_key", ApplicationProperties.getGamespotApiKey());
			put("format", "json");
			put("field_list", "name,description,deck,genres,image");

		}
	};

	private static WebClient CLIENT = WebClient.builder().codecs(consumer)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

	static public void printRequest(String url, Map<String, String> queryParams) {
		log.info("==================== REQUEST START ====================");

		log.info(url);

		if (queryParams.size() != 0) {
			log.info("Displaying Query Parameters list:");
			for (Entry<String, String> queryParam : queryParams.entrySet()) {
				log.info("Key: {}; Value: {}", queryParam.getKey(), queryParam.getValue());
			}
		}

		log.info("===================== REQUEST END =====================");
	}

	static public GamespotResponse sendGamespotRequest(String method, String url, Map<String, String> queryParams)
			throws URISyntaxException, IOException, InterruptedException {
		UriBuilder uriBuilder = new DefaultUriBuilderFactory(ApplicationProperties.getGamespotUrl() + url).builder();

		for (Entry<String, String> queryParam : defaultUriVariables.entrySet()) {
			uriBuilder = uriBuilder.queryParam(queryParam.getKey(), queryParam.getValue());
		}
		if (queryParams.size() != 0) {
			for (Entry<String, String> queryParam : queryParams.entrySet()) {
				uriBuilder = uriBuilder.queryParam(queryParam.getKey(), queryParam.getValue());
			}
		}

		RequestHeadersSpec<?> request = CLIENT.method(HttpMethod.valueOf(method)).uri(uriBuilder.build());

		printRequest(ApplicationProperties.getGamespotUrl() + url, queryParams);

		ResponseSpec responseSpec = request.retrieve();

		ResponseEntity<GamespotResponse> response = responseSpec.toEntity(GamespotResponse.class).block();

		return response.getBody();
	}

	static public GamespotResponse getGamespotResponseBody(ClientResponse response) {
		return response.bodyToMono(GamespotResponse.class).block();
	}

	public static String predicateToString(Predicate predicate) {
		return predicate instanceof BooleanOperation ? ((BooleanOperation) predicate).getArgs().toString()
				: predicate.toString();
	}
}
