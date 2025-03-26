package org.example.booktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.UserCreateDto;
import org.example.booktracker.exception.UserAlreadyExists;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.mapper.UserMapper;
import org.example.booktracker.repository.UserRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.SuccessCreated;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final SuccessCreatedMapper successCreatedMapper;

    // static final values
    private static final LocalDateTime dateTime = LocalDateTime.now();

    @Transactional
    public SuccessCreated saveUser(
            UserCreateDto user
    ) {
        isExistsByEmail(user.email());

        var savedUser = userRepository.save(
                userMapper.toEntity(user, passwordEncoder.encode(user.password()))
        );

        return successCreatedMapper.toSuccessCreated(
                savedUser.toString(),
                ConstantMessages.USER_SUCCESS_CREATED.getDescription(),
                dateTime.toString()
        );
    }

    private void isExistsByEmail(
            String email
    ) {
        if (userRepository.isExists(email)) throw new UserAlreadyExists(
                "User with email = %s already exists!".formatted(email)
        );
    }

}
