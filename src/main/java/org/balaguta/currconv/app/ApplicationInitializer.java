package org.balaguta.currconv.app;

import org.balaguta.currconv.data.RoleRepository;
import org.balaguta.currconv.data.UserRepository;
import org.balaguta.currconv.data.entity.Role;
import org.balaguta.currconv.data.entity.User;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

public class ApplicationInitializer {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CurrconvProperties properties;

    public ApplicationInitializer(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  CurrconvProperties properties) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.properties = properties;
    }

    @EventListener
    @Transactional
    public void setupApplication(ContextRefreshedEvent event) {

        // create admin user
        User user = new User();
        user.setEmail(properties.getAdmin().getEmail());
        user.setPassword(properties.getAdmin().getPassword());

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        user.getRoles().add(adminRole);

        userRepository.save(user);
    }

}
