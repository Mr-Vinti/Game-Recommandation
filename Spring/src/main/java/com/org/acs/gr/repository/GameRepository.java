package com.org.acs.gr.repository;

import com.org.acs.gr.domain.Game;
import com.org.acs.gr.domain.QGame;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository
		extends JpaRepository<Game, Integer>, QuerydslPredicateExecutor<Game>, QuerydslBinderCustomizer<QGame> {

	@Override
	default void customize(QuerydslBindings bindings, QGame root) {
	}

	List<Game> findByGameIdIn(List<Integer> gameIds);
	
	@Query(value = "Select g from Game g join g.genres r where r.genreId = :genreId")
	List<Game> findByGameGenre(Integer genreId);
}
