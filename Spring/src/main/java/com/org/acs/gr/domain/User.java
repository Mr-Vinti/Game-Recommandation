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
import javax.persistence.Id;
import javax.persistence.Table;

import com.org.acs.gr.dto.UserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@Entity
@Table(schema = "dbo", name = "USR")
public class User {

    @Id
    @Column(name = "USR_ID")
    private String userId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FRST_NAM")
    private String firstName;

    @Column(name = "LST_NAM")
    private String lastName;

    @Column(name = "CRNT_PTS")
    private Integer currentPoints;

    @Column(name = "TTL_PTS")
    private Integer totalPoints;

    @Column(name = "ROLE_CD")
    private String roleCode;

    public static UserDto toDto(User entity, boolean withRole) {
        UserDto userDto = UserDto.builder()
                .id(entity.userId)
                .email(entity.email)
                .firstName(entity.firstName)
                .lastName(entity.lastName)
                .currentPoints(entity.currentPoints)
                .totalPoints(entity.totalPoints)
                .build();

        return userDto;
    }
}
