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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    private Integer userId;

    @Column(name = "USR_UUID")
    private String userUuid;

    public static UserDto toDto(User entity) {
        UserDto userDto = UserDto.builder()
                .id(entity.userId)
                .uuid(entity.userUuid)
                .build();

        return userDto;
    }
}
