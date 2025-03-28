package org.example.booktracker.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.user.UserProfileInfoDto;
import org.example.booktracker.mapper.UserMapper;
import org.example.booktracker.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "userProfile", key = "#id")
    public UserProfileInfoDto getUserById(
            Long id
    ) {
        return userMapper.toDto(
                userRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(
                                "User with id = %s not found!".formatted(id)
                        )
                )
        );
    }
}
