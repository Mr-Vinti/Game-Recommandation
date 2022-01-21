package com.org.acs.gr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.org.acs.gr.bean.StringResponse;
import com.org.acs.gr.client.PythonClient;
import com.org.acs.gr.domain.Game;
import com.org.acs.gr.domain.Genre;
import com.org.acs.gr.domain.QGame;
import com.org.acs.gr.domain.UserRecommandation;
import com.org.acs.gr.domain.UserRecommandationPK;
import com.org.acs.gr.dto.GameDto;
import com.org.acs.gr.dto.GamesDto;
import com.org.acs.gr.repository.GameRepository;
import com.org.acs.gr.repository.UserRecommandationRepository;
import com.org.acs.gr.util.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameService {
	private final GameRepository gameRepository;
	private final UserRecommandationRepository userRecommandationRepository;
	private final GenreService genreService;
	private final PythonClient pythonClient;

	public Page<GameDto> getGamesByPredicate(Predicate predicate, Pageable pageable) {
		QGame qGame = QGame.game;

		OptionalBooleanBuilder optionalBooleanBuilder = new OptionalBooleanBuilder(qGame.isNotNull());

		return gameRepository.findAll(optionalBooleanBuilder.build().and(predicate), pageable).map(Game::toDto);
	}

	public StringResponse saveGames(List<GameDto> gameDtos, List<String> genres) {
		List<String> existingGenres = genreService.getGenreDtos();

		List<String> newGenres = new ArrayList<>();
		newGenres.addAll(genres);
		newGenres.removeAll(existingGenres);

		genreService.saveGenres(newGenres);

		List<Genre> allGenres = genreService.getGenres();

		Map<String, Genre> genreMap = new HashMap<>();
		for (Genre genre : allGenres) {
			genreMap.put(genre.getName(), genre);
		}

		List<Game> games = new ArrayList<>();
		for (GameDto game : gameDtos) {
			Set<Genre> gameGenres = new LinkedHashSet<>();
			for (String genre : game.getGenres()) {
				gameGenres.add(genreMap.get(genre));
			}

			games.add(Game.builder().name(game.getName()).description(game.getDescription()).image(game.getImage())
					.genres(gameGenres).build());
		}

		for (List<Game> partitionedGames : Lists.partition(games, 2010)) {
			gameRepository.saveAll(partitionedGames);
		}

		return new StringResponse("Saved " + games.size() + " games successfully.");
	}

	public GamesDto getGames(GamesDto games) {
		if (StringUtils.isBlank(games.getDescription())) {
			List<Integer> gameIds = userRecommandationRepository.findByIdUserId(games.getUser().getId()).stream()
					.map(UserRecommandation::getId).map(UserRecommandationPK::getGameId).distinct()
					.collect(Collectors.toList());

			List<Game> gameList = gameRepository.findByGameIdIn(gameIds);
			games.setGames(gameList.stream().map(Game::toDto).collect(Collectors.toList()));

			return games;
		}

		List<String> genreNames = pythonClient.checkOrders(StringUtils.trim(games.getDescription()));

		List<Genre> genres = genreNames.stream().map((genre) -> genreService.getGenreByName(genre))
				.collect(Collectors.toList());

		List<Game> gameList = genres.stream()
				.map((genre) -> gameRepository.findByGameGenre(genre.getGenreId()).subList(0, 5))
				.flatMap((list) -> list.stream()).distinct().collect(Collectors.toList());
		games.setGames(gameList.stream().map(Game::toDto).collect(Collectors.toList()));
		
		List<UserRecommandation> userRecommandations = gameList.stream()
				.map((game) -> UserRecommandation.builder()
						.id(new UserRecommandationPK(games.getUser().getId(), game.getGameId())).build())
				.collect(Collectors.toList());
		
		userRecommandationRepository.deleteByIdUserId(games.getUser().getId());
		
		userRecommandationRepository.saveAll(userRecommandations);

		return games;
	}
}
