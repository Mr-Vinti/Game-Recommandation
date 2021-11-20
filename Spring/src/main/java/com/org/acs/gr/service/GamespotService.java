package com.org.acs.gr.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.org.acs.gr.bean.GamespotGame;
import com.org.acs.gr.bean.GamespotResponse;
import com.org.acs.gr.util.UtilService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class GamespotService {

	public List<GamespotGame> getGames() throws URISyntaxException, IOException, InterruptedException {
		Map<String, String> queryParams = new HashMap<>();

		GamespotResponse gamespotResponse = UtilService.sendGamespotRequest("GET", "games/", queryParams);
		log.info("Returned {} games out of {}", gamespotResponse.getGames().size(), gamespotResponse.getNumberOfTotalResults());

		return gamespotResponse.getGames();
	}
}
