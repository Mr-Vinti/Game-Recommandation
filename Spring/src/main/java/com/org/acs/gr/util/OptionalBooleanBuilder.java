package com.org.acs.gr.util;

import java.util.Collection;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;

public class OptionalBooleanBuilder {

	private BooleanExpression predicate;

	public OptionalBooleanBuilder(BooleanExpression predicate) {
		this.predicate = predicate;
	}

	public <T> OptionalBooleanBuilder notNullAnd(Function<T, BooleanExpression> expressionFunction, T value) {
		if (value != null) {
			return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(value)));
		}
		return this;
	}
	
	public <T> OptionalBooleanBuilder notNullOr(Function<T, BooleanExpression> expressionFunction, T value) {
		if (value != null) {
			return new OptionalBooleanBuilder(predicate.or(expressionFunction.apply(value)));
		}
		return this;
	}

	public OptionalBooleanBuilder notEmptyAnd(Function<String, BooleanExpression> expressionFunction, String value) {
		if (!StringUtils.isBlank(value)) {
			return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(value)));
		}
		return this;
	}

	public <T> OptionalBooleanBuilder notEmptyAnd(Function<Collection<T>, BooleanExpression> expressionFunction,
			Collection<T> collection) {
		if (!CollectionUtils.isEmpty(collection)) {
			return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(collection)));
		}
		return this;
	}

	public BooleanExpression build() {
		return predicate;
	}

	public OptionalBooleanBuilder notEmptyAnd(BooleanExpression expression) {
		return new OptionalBooleanBuilder(predicate.and(expression));
	}
	
	public OptionalBooleanBuilder notEmptyOr(BooleanExpression expression) {
		return new OptionalBooleanBuilder(predicate.or(expression));
	}
}
