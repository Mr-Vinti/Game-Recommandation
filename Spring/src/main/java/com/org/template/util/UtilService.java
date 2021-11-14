package com.org.template.util;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanOperation;

public class UtilService {

	public static String predicateToString(Predicate predicate) {
		return predicate instanceof BooleanOperation ? ((BooleanOperation) predicate).getArgs().toString()
				: predicate.toString();
	}
}
