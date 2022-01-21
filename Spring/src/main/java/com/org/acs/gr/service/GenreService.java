package com.org.acs.gr.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.org.acs.gr.bean.StringResponse;
import com.org.acs.gr.domain.Genre;
import com.org.acs.gr.repository.GenreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreService {
	private final GenreRepository genreRepository;

	public List<Genre> getGenres() {
		return genreRepository.findAll();
	}

	public List<String> getGenreDtos() {
		return getGenres().stream().map(Genre::getName).collect(Collectors.toList());
	}

	public Genre getGenreByName(String name) {
		return genreRepository.findByName(name).get();
	}

	public StringResponse saveGenres(List<String> genres) {

		genreRepository.saveAll(
				genres.stream().map((genre) -> Genre.builder().name(genre).build()).collect(Collectors.toList()));

		return new StringResponse("Saved " + genres.size() + " genres successfully.");
	}
}
