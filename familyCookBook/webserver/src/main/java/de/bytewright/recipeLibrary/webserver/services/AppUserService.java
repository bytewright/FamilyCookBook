package de.bytewright.recipeLibrary.webserver.services;

import java.util.List;

import org.bytewright.recipeLibrary.api.generated.UserInformation;

public interface AppUserService {
  List<UserInformation> getAllUsers();
}
