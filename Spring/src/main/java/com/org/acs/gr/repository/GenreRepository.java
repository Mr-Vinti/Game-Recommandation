package com.org.acs.gr.repository;

import com.org.acs.gr.domain.Genre;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

	Optional<Genre> findByName(String name);
}
