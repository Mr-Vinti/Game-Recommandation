package com.org.acs.gr.rest;

import com.org.acs.gr.bean.GamespotGame;
import com.org.acs.gr.domain.User;
import com.org.acs.gr.dto.UserDto;
import com.org.acs.gr.service.GamespotService;
import com.org.acs.gr.service.UserService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Gamespot Controller")
@RequestMapping("/api/gamespot")
@AllArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class GamespotController {

	private GamespotService gamespotService;

	@ApiOperation("Get All Gamespot Games Method")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 400, message = "Malformed request"),
			@ApiResponse(code = 500, message = "Internal error") })
	@GetMapping
	public ResponseEntity<List<GamespotGame>> getGamespotGames()
			throws URISyntaxException, IOException, InterruptedException {

		return ResponseEntity.ok(gamespotService.getGames());
	}

}