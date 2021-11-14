package com.org.acs.gr.repository;

import com.org.acs.gr.domain.User;
import com.org.acs.gr.domain.QUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>,
		QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
	}

}
