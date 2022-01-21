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
public class UserRecommandationPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -602327453005459916L;

    @Column(name = "USR_ID")
    private Integer userId;

    @Column(name = "GAME_ID")
    private Integer gameId;
}