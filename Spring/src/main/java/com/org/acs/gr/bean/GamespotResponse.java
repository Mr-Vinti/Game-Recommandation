package com.org.acs.gr.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GamespotResponse {
	@JsonAlias("number_of_total_results")
	private Integer numberOfTotalResults;

	@JsonAlias("results")
	private List<GamespotGame> games;
}
