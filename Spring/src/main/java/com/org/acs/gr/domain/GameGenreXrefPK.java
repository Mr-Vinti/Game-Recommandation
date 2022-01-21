package com.org.acs.gr.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class GameGenreXrefPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -602327453005459916L;

    @Column(name = "GAME_ID")
    private Integer gameId;

    @Column(name = "GENRE_ID")
    private Integer genreId;
}
