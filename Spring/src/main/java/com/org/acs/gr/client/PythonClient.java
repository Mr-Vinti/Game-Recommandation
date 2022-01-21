package com.org.acs.gr.client;

import java.util.List;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.org.acs.gr.client.dto.PythonDto;
import com.org.acs.gr.client.dto.PythonResponseDto;
import com.org.acs.gr.config.ApplicationProperties;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor
@Log4j2
public class PythonClient {

	public List<String> checkOrders(String description) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl(CacheControl.noCache());

		PythonDto dto = PythonDto.builder().description(description).build();

		HttpEntity<PythonDto> request = new HttpEntity<>(dto, headers);

		try {
			ResponseEntity<PythonResponseDto> response = restTemplate.postForEntity(
					ApplicationProperties.getPythonServiceUrl() + "genres", request, PythonResponseDto.class);

			List<String> genres = response.getBody().getGenres();
			log.info("Returned {} genres for description {}: {}", genres.size(), description, genres.toString());
			return genres;
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Failed to send description to Python Web-service: " + ex.getMessage());
		} catch (ResourceAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Python api url: " + ex.getMessage());
		}
	}
}
