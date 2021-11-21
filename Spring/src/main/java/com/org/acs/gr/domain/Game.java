package com.org.acs.gr.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.org.acs.gr.dto.GameDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@Entity
@Table(schema = "dbo", name = "GAME")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_game")
	@GenericGenerator(name = "hilo_game", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "GAME_SEQ"), @Parameter(name = "initial_value", value = "0"),
			@Parameter(name = "increment_size", value = "30"), @Parameter(name = "optimizer", value = "hilo") })
	@Column(name = "GAME_ID")
	private Integer gameId;

	@Column(name = "NAM")
	private String name;

	@Column(name = "[DESC]")
	private String description;

	@Column(name = "IMG")
	private String image;

	@ManyToMany
	@JoinTable(name = "GAME_GENRE_XREF", joinColumns = @JoinColumn(name = "GAME_ID"), inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
	private Set<Genre> genres;

	public static GameDto toDto(Game entity) {
		GameDto gameDto = GameDto.builder().id(entity.gameId).name(entity.name).description(entity.description)
				.image(entity.image).genres(entity.genres.stream().map(Genre::getName).collect(Collectors.toList()))
				.build();

		return gameDto;
	}
}
