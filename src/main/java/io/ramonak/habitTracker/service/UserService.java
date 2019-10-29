package io.ramonak.habitTracker.service;

import com.vaadin.flow.component.UI;
import io.ramonak.habitTracker.entity.Role;
import io.ramonak.habitTracker.entity.User;
import io.ramonak.habitTracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
//    private final PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        if (userExists(user.getEmail())) {
            throw new UserAlreadyExistsException("There is an account with this email " + user.getEmail());
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setRole(Role.USER);
        newUser.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        User savedUser = userRepository.save(newUser);
        UI.getCurrent().navigate("login");
        return savedUser;
    }

    private boolean userExists(final String email) {
        return userRepository.findByEmailIgnoreCase(email) != null;
    }
}
