package com.org.acs.gr.rest;

import com.org.acs.gr.domain.Game;
import com.org.acs.gr.dto.GameDto;
import com.org.acs.gr.dto.GamesDto;
import com.org.acs.gr.service.GameService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Games Controller")
@RequestMapping("/api/games")
@AllArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class GameController {

	private GameService gameService;

	@ApiOperation("Get All Games Method")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 400, message = "Malformed request"),
			@ApiResponse(code = 500, message = "Internal error") })
	@GetMapping
	public ResponseEntity<Page<GameDto>> getGames(@QuerydslPredicate(root = Game.class) Predicate predicate,
			Pageable pageable) {
		log.info("Retrieving page {} of size {} of games with predicate {} requested by {}", pageable.getPageNumber(),
				pageable.getPageSize());

		return ResponseEntity.ok(gameService.getGamesByPredicate(predicate, pageable));
	}
	
	@ApiOperation("Get Games for user Method")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 400, message = "Malformed request"),
			@ApiResponse(code = 500, message = "Internal error") })
	@PostMapping
	public ResponseEntity<GamesDto> getGames(@RequestBody(required = true) GamesDto games) {
		log.info("Retrieving games for user with id {} and description {}", games.getUser().getId(),
				games.getDescription());

		return ResponseEntity.ok(gameService.getGames(games));
	}

}
