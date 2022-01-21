package com.org.acs.gr.rest;

import com.org.acs.gr.bean.GamespotGame;
import com.org.acs.gr.service.GamespotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Gamespot Controller")
@RequestMapping("/api/gamespot")
@AllArgsConstructor
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
