package com.example.todo;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class TaskTextCoercing implements Coercing<TaskText, String> {

	@Override
	public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
		if (dataFetcherResult instanceof TaskText) {
			return ((TaskText) dataFetcherResult).getValue();
		}
		throw new CoercingSerializeException();
	}

	@Override
	public TaskText parseValue(Object input) throws CoercingParseValueException {
		if (input instanceof String) {
			return new TaskText((String) input);
		}
		throw new CoercingParseValueException();
	}

	@Override
	public TaskText parseLiteral(Object input) throws CoercingParseLiteralException {
		if (input instanceof StringValue) {
			return new TaskText(((StringValue) input).getValue());
		}
		throw new CoercingParseLiteralException();
	}
}
