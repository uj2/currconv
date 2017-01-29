package org.balaguta.currconv.app;

import org.balaguta.currconv.data.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AppUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public AppUser(String username, String password, User user,
                   Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof AppUser) {
            return getUsername().equals(((AppUser) rhs).getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

}
