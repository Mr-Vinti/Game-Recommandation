package com.org.acs.gr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
@Setter
public class ApplicationProperties {
	private static String gamespotUrl;
	private static String gamespotApiKey;


	public static String getGamespotUrl() {
		return gamespotUrl;
	}

	public void setGamespotUrl(String gamespotUrl) {
		ApplicationProperties.gamespotUrl = gamespotUrl;
	}

	public static String getGamespotApiKey() {
		return gamespotApiKey;
	}

	public void setGamespotApiKey(String gamespotApiKey) {
		ApplicationProperties.gamespotApiKey = gamespotApiKey;
	}
}
