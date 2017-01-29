package org.balaguta.currconv.service.impl;

import org.balaguta.currconv.app.AppUser;
import org.balaguta.currconv.data.RoleRepository;
import org.balaguta.currconv.data.UserRepository;
import org.balaguta.currconv.data.entity.User;
import org.balaguta.currconv.service.UserService;
import org.balaguta.currconv.web.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Assert.isInstanceOf(AppUser.class, principal);
        AppUser user = (AppUser) principal;
        return user.getUser();
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    @Transactional
    public User registerUser(@Valid UserDto userInfo) {
        User user = new User();
        user.setEmail(userInfo.getEmail());
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.getRoles().add(roleRepository.findByName("ROLE_USER"));
        return userRepository.save(user);
    }
}
