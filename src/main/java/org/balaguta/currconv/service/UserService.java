package org.balaguta.currconv.service;

import org.balaguta.currconv.data.entity.User;
import org.balaguta.currconv.web.UserDto;

public interface UserService {
    User getCurrentUser();
    boolean userExists(String email);
    User registerUser(UserDto userInfo);
}
