package de.bytewright.recipeLibrary.webserver.security;

import java.time.Instant;
import java.util.Optional;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationSession extends AbstractAuthenticatedWebSession {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSession.class);
  private Instant creationInstant;
  private long userDbId;

  public ApplicationSession(Request request) {
    super(request);
    LOGGER.warn("Created new Session obj for request {}", request.getUrl());
  }

  @Override
  public Roles getRoles() {
    LOGGER.warn("TODO! Roles are requested", new Exception());
    return new Roles(Roles.ADMIN);
  }

  @Override
  public boolean isSignedIn() {
    LOGGER.warn("TODO! isSignedIn is requested", new Exception());
    return true;
  }

  public Instant getCreationInstant() {
    return creationInstant;
  }

  public void setCreationInstant(Instant creationInstant) {
    this.creationInstant = creationInstant;
  }

  /**
   * Return session of current user, provided by wicket
   */
  public static ApplicationSession get() {
    Session session = Session.get();
    if (session instanceof ApplicationSession) {
      return (ApplicationSession) session;
    }
    throw new IllegalStateException("Session is of unexpected class: " + session.getClass());
  }

  public long getUserDbId() {
    return userDbId;
  }

  public void setUserDbId(long userDbId) {
    this.userDbId = userDbId;
  }
}
