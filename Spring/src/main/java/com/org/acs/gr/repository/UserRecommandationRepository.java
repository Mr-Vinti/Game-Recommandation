package com.org.acs.gr.repository;

import com.org.acs.gr.domain.QUserRecommandation;
import com.org.acs.gr.domain.UserRecommandation;
import com.org.acs.gr.domain.UserRecommandationPK;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRecommandationRepository
		extends JpaRepository<UserRecommandation, UserRecommandationPK>, QuerydslPredicateExecutor<UserRecommandation>, QuerydslBinderCustomizer<QUserRecommandation> {

	@Override
	default void customize(QuerydslBindings bindings, QUserRecommandation root) {
	}

	List<UserRecommandation> findByIdUserId(Integer userId);
	
	@Transactional
	@Modifying
	void deleteByIdUserId(Integer userId);
	
}
