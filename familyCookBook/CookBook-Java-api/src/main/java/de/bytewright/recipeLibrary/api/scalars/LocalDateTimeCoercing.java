package de.bytewright.recipeLibrary.api.scalars;

import static graphql.scalars.util.Kit.typeName;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.function.Function;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public final class LocalDateTimeCoercing implements Coercing<LocalDateTime, String> {

  private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  @Override
  public String serialize(Object input) throws CoercingSerializeException {
    ZonedDateTime offsetDateTime;
    if (input instanceof LocalDateTime) {
      offsetDateTime = ZonedDateTime.of((LocalDateTime) input, ZoneOffset.systemDefault());
    } else if (input instanceof OffsetDateTime) {
      offsetDateTime = ((OffsetDateTime) input).toZonedDateTime();
    } else if (input instanceof ZonedDateTime) {
      offsetDateTime =(ZonedDateTime) input;
    } else if (input instanceof String) {
      offsetDateTime = parseLocalDateTime(input.toString(), CoercingSerializeException::new);
    } else {
      throw new CoercingSerializeException(
          "Expected something we can convert to 'java.time.LocalDateTime' but was '" + typeName(input) + "'."
      );
    }
    try {
      return FORMATTER.format(offsetDateTime);
    } catch (DateTimeException e) {
      throw new CoercingSerializeException(
          "Unable to turn TemporalAccessor into LocalDateTime because of : '" + e.getMessage() + "'."
      );
    }
  }

  @Override
  public LocalDateTime parseValue(Object input) throws CoercingParseValueException {
    LocalDateTime localDateTime;
    if (input instanceof LocalDateTime) {
      localDateTime = (LocalDateTime) input;
    } else if (input instanceof OffsetDateTime) {
      localDateTime = ((OffsetDateTime) input).toLocalDateTime();
    } else if (input instanceof ZonedDateTime) {
      localDateTime = ((ZonedDateTime) input).toLocalDateTime();
    } else if (input instanceof String) {
      localDateTime =
          parseLocalDateTime(input.toString(), CoercingParseValueException::new).withZoneSameInstant(ZoneId.systemDefault())
              .toLocalDateTime();
    } else {
      throw new CoercingParseValueException(
          "Expected a 'String' but was '" + typeName(input) + "'."
      );
    }
    return localDateTime;
  }

  @Override
  public LocalDateTime parseLiteral(Object input) throws CoercingParseLiteralException {
    if (!(input instanceof StringValue)) {
      throw new CoercingParseLiteralException(
          "Expected AST type 'StringValue' but was '" + typeName(input) + "'."
      );
    }
    return parseLocalDateTime(((StringValue) input).getValue(), CoercingParseLiteralException::new).toLocalDateTime();
  }

  private ZonedDateTime parseLocalDateTime(String dateString, Function<String, RuntimeException> exceptionMaker) {
    try {
      TemporalAccessor temporalAccessor = FORMATTER.parse(dateString);
      return ZonedDateTime.from(temporalAccessor);
    } catch (DateTimeParseException e) {
      throw exceptionMaker.apply("Invalid RFC3339 value : '" + dateString + "'. because of : '" + e.getMessage() + "'");
    }
  }
}
