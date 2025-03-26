package org.example.booktracker.utils;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.exception.UserAlreadyExists;
import org.example.booktracker.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilsMethods {
    private final UserRepository userRepository;

    public void isExistsByEmail(
            String email
    ) {
        if (userRepository.isExists(email)) throw new UserAlreadyExists(
                "User with email = %s already exists!".formatted(email)
        );
    }
}
