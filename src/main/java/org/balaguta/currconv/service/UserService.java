package org.balaguta.currconv.service;

import org.balaguta.currconv.data.entity.Country;
import org.balaguta.currconv.data.entity.User;
import org.balaguta.currconv.web.UserDto;

import java.util.List;

public interface UserService {
    User getCurrentUser();
    boolean userExists(String email);
    User registerUser(UserDto userInfo);
    List<Country> getCountries();
}
