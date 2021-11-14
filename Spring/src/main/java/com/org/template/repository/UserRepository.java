package com.org.template.repository;

import com.org.template.domain.QUser;
import com.org.template.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>,
		QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
	}
	
    Optional<User> findByEmail(String email);

    List<User> findByRoleCode(String roleCode);

}
