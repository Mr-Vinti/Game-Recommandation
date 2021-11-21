package com.org.acs.gr.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.org.acs.gr.bean.CsvGameBean;
import com.org.acs.gr.bean.GamespotGame;
import com.org.acs.gr.bean.GamespotGenre;
import com.org.acs.gr.bean.GamespotResponse;
import com.org.acs.gr.dto.GameDto;
import com.org.acs.gr.util.UtilService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class GamespotService {
	private final GameService gameService;
	private final CsvService csvService;

	private List<GamespotGame> getGamespotGames() throws URISyntaxException, IOException, InterruptedException {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("limit", "1");
		GamespotResponse gamespotResponse = UtilService.sendGamespotRequest("GET", "games/", queryParams);

		Integer totalResults = gamespotResponse.getNumberOfTotalResults();
		Double numberOfRequests = totalResults / 100.0;
		Integer numberOfRequestsRounded = (int) Math.ceil(numberOfRequests);

		List<GamespotGame> games = new ArrayList<>();

		for (int i = 0; i < numberOfRequestsRounded; i++) {
			queryParams.clear();
			Integer offset = i * 100;
			queryParams.put("offset", offset.toString());
			gamespotResponse = UtilService.sendGamespotRequest("GET", "games/", queryParams);
			games.addAll(gamespotResponse.getGames());
			log.info("Returned {} games out of {}", games.size(), gamespotResponse.getNumberOfTotalResults());
		}

		return games;
	}

	private List<GameDto> convertGamespotGamesToDto(List<GamespotGame> games) {
		List<GameDto> preprocessedGames = new ArrayList<>();

		for (GamespotGame game : games) {
			if (StringUtils.isNotBlank(game.getName())
					&& (StringUtils.isNotBlank(game.getDescription()) || StringUtils.isNotBlank(game.getDeck()))
					&& !CollectionUtils.isEmpty(game.getGenres()) && game.getImage() != null
					&& StringUtils.isNotBlank(game.getImage().getOriginal())
					&& game.getImage().getOriginal().length() < 500
					&& game.getName().length() < 255) {
				String description = StringUtils.trim(game.getDescription());
				if (StringUtils.isNotBlank(game.getDeck())) {
					description = description + (StringUtils.isNotBlank(description) ? ". " : "")
							+ StringUtils.trim(game.getDeck());
				}
				StringUtils.substring(description, 0, description.length() > 8000 ? 8000 : description.length());
				List<String> genres = game.getGenres().stream()
						.filter((genre) -> StringUtils.isNotBlank(genre.getName()))
						.map(genre -> StringUtils.trim(genre.getName())).collect(Collectors.toList());

				preprocessedGames.add(GameDto.builder().name(StringUtils.trim(game.getName())).description(description)
						.image(StringUtils.trim(game.getImage().getOriginal())).genres(genres).build());
			}
		}

		return preprocessedGames;
	}

	private List<String> getSortedGenres(List<GameDto> games) {
		Set<String> genres = games.stream().map(GameDto::getGenres).collect(Collectors.toList()).stream()
				.flatMap(List::stream).collect(Collectors.toSet());

		List<String> orderedGenres = genres.stream().collect(Collectors.toList());

		Collections.sort(orderedGenres);

		return orderedGenres;
	}

	private List<CsvGameBean> convertGameDtosToCsvBeans(List<GameDto> games, List<String> genres) {
		List<CsvGameBean> csvGames = new ArrayList<>();

		for (GameDto game : games) {
			List<String> gameGenres = new ArrayList<>();
			for (String genre : genres) {
				if (game.getGenres().contains(genre)) {
					gameGenres.add("1");
				} else {
					gameGenres.add("0");
				}
			}

			csvGames.add(CsvGameBean.builder().name(game.getName()).description(game.getDescription())
					.genres(gameGenres).build());
		}

		return csvGames;
	}

	public List<GamespotGame> getGames() throws URISyntaxException, IOException, InterruptedException {
		List<GamespotGame> games = getGamespotGames();

		List<GameDto> preprocessedGames = convertGamespotGamesToDto(games);

		List<String> genres = getSortedGenres(preprocessedGames);

		gameService.saveGames(preprocessedGames, genres);

		List<CsvGameBean> csvGames = convertGameDtosToCsvBeans(preprocessedGames, genres);

		csvService.exportCsv(csvGames, genres);

		return games;
	}
}
