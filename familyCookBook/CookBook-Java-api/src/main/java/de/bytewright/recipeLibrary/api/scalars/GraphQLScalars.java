package de.bytewright.recipeLibrary.api.scalars;

import graphql.schema.GraphQLScalarType;

public class GraphQLScalars {

  public static final GraphQLScalarType DATE_TIME = GraphQLScalarType.newScalar()
      .name("DateTime")
      .description("An RFC-3339 compliant DateTime Scalar")
      .coercing(new LocalDateTimeCoercing())
      .build();

  public static final GraphQLScalarType EMAIL = GraphQLScalarType.newScalar()
      .name("Email")
      .description("A scalar representing an email address.")
      .coercing(new EmailCoercing())
      .build();
}
