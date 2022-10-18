package de.bytewright.recipeLibrary.api.scalars;

import static graphql.scalars.util.Kit.typeName;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class EmailCoercing implements Coercing<String, String> {

  @Override
  public String serialize(Object input) throws CoercingSerializeException {
    String value = String.valueOf(input);
    return matches(value, CoercingSerializeException::new);
  }

  @Override
  public String parseValue(Object input) throws CoercingParseValueException {
    String value = String.valueOf(input);
    return matches(value, CoercingParseValueException::new);
  }

  @Override
  public String parseLiteral(Object input) throws CoercingParseLiteralException {
    if (!(input instanceof StringValue)) {
      throw new CoercingParseLiteralException(
          "Expected AST type 'StringValue' but was '" + typeName(input) + "'."
      );
    }
    String value = ((StringValue) input).getValue();
    return matches(value, CoercingParseLiteralException::new);
  }

  private String matches(String value, Function<String, RuntimeException> exceptionMaker) {
    if (EmailValidator.getInstance().isValid(StringUtils.trimToEmpty(value))) {
      return value;
    }
    throw exceptionMaker.apply("The given value is not a valid email address.");
  }
}
