package com.org.acs.gr.service;

import com.org.acs.gr.domain.User;
import com.org.acs.gr.dto.UserDto;
import com.org.acs.gr.repository.UserRepository;
import com.org.acs.gr.util.OptionalBooleanBuilder;
import com.org.acs.gr.domain.QUser;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

	private final UserRepository userRepository;

	public Page<UserDto> getUsersByPredicate(Predicate predicate, String email, List<String> roleCodes, Pageable pageable) {
		QUser qUser = QUser.user;

		OptionalBooleanBuilder optionalBooleanBuilder = new OptionalBooleanBuilder(qUser.isNotNull())
				.notEmptyAnd(qUser.email::contains, email).notEmptyAnd(qUser.roleCode::in, roleCodes);

		return userRepository.findAll(optionalBooleanBuilder.build().and(predicate), pageable)
				.map(user -> User.toDto(user, true));
	}

}
