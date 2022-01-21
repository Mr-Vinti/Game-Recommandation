package com.org.acs.gr.service;

import com.org.acs.gr.domain.User;
import com.org.acs.gr.dto.UserDto;
import com.org.acs.gr.repository.UserRepository;
import com.org.acs.gr.util.OptionalBooleanBuilder;
import com.org.acs.gr.domain.QUser;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanOperation;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

	private final UserRepository userRepository;

	public Page<UserDto> getUsersByPredicate(Predicate predicate, Pageable pageable) {
		QUser qUser = QUser.user;

		if (((BooleanOperation) predicate).getArgs().get(0).toString().equals("user.userUuid")) {
			String uuid = ((BooleanOperation) predicate).getArgs().get(1).toString();
			try {
				UUID.fromString(uuid);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must input a valid UUID");
			}
		}

		OptionalBooleanBuilder optionalBooleanBuilder = new OptionalBooleanBuilder(qUser.isNotNull());

		return userRepository.findAll(optionalBooleanBuilder.build().and(predicate), pageable)
				.map(user -> User.toDto(user));
	}

	public UserDto getNewUserId() {
		UUID uuid;
		do {
			uuid = UUID.randomUUID();
		} while (userRepository.findByUserUuid(uuid.toString()).isPresent());

		log.info("Generated new UUID {}", uuid.toString());

		User user = User.builder().userUuid(uuid.toString()).build();
		userRepository.save(user);

		user = userRepository.findByUserUuid(uuid.toString()).get();

		log.info("Saved UUID {} with ID", uuid.toString(), user.getUserId());

		return User.toDto(user);
	}

}
