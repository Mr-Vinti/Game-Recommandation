package com.org.acs.gr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.org.acs.gr.bean.StringResponse;
import com.org.acs.gr.domain.Game;
import com.org.acs.gr.domain.Genre;
import com.org.acs.gr.domain.QGame;
import com.org.acs.gr.dto.GameDto;
import com.org.acs.gr.repository.GameRepository;
import com.org.acs.gr.util.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class GameService {
	private final GameRepository gameRepository;
	private final GenreService genreService;

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
}
