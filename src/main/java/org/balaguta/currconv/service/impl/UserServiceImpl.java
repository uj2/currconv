package org.balaguta.currconv.service.impl;

import org.balaguta.currconv.app.AppUser;
import org.balaguta.currconv.data.CountryRepository;
import org.balaguta.currconv.data.RoleRepository;
import org.balaguta.currconv.data.UserRepository;
import org.balaguta.currconv.data.entity.Address;
import org.balaguta.currconv.data.entity.Country;
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
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           CountryRepository countryRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

        Address address = new Address();
        address.setLine1(userInfo.getAddress().getLine1());
        address.setLine2(userInfo.getAddress().getLine2());
        address.setZip(userInfo.getAddress().getZip());
        Country country = countryRepository.findOne(userInfo.getAddress().getCountry());
        Assert.notNull(country, "[country] required; must not be null");
        address.setCountry(country);
        user.setAddress(address);


        user.getRoles().add(roleRepository.findByName("ROLE_USER"));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountries() {
        return countryRepository.findAllByOrderByName();
    }
}
