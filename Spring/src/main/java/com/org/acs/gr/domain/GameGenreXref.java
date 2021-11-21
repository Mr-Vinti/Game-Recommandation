package com.org.acs.gr.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@Entity
@Table(schema = "dbo", name = "GAME_GENRE_XREF")
public class GameGenreXref {

    @EmbeddedId
    private GameGenreXrefPK id;

//    public static UserDto toDto(Game entity, boolean withRole) {
//        UserDto userDto = UserDto.builder()
//                .id(entity.userId)
//                .uuid(entity.userUuid)
//                .build();
//
//        return userDto;
//    }
}
