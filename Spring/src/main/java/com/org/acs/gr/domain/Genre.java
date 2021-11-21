package com.org.acs.gr.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@Entity
@Table(schema = "dbo", name = "GENRE")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_genre")
	@GenericGenerator(name = "hilo_genre", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "GENRE_SEQ"), @Parameter(name = "initial_value", value = "0"),
			@Parameter(name = "increment_size", value = "30"), @Parameter(name = "optimizer", value = "hilo") })
    @Column(name = "GENRE_ID")
    private Integer genreId;

    @Column(name = "NAM")
    private String name;

//    public static UserDto toDto(Game entity, boolean withRole) {
//        UserDto userDto = UserDto.builder()
//                .id(entity.userId)
//                .uuid(entity.userUuid)
//                .build();
//
//        return userDto;
//    }
}
