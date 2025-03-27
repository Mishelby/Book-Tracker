package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.EvenListener.EmailEvent;
import org.example.booktracker.domain.Author.AuthorCreateDto;
import org.example.booktracker.mapper.AuthorMapper;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.repository.AuthorRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.SuccessCreated;
import org.example.booktracker.utils.UtilsMethods;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthorRegistrationService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final SuccessCreatedMapper successCreatedMapper;
    private final UtilsMethods utilsMethods;
    private final AuthorMapper authorMapper;
    private final ApplicationEventPublisher eventPublisher;

    // static final values
    private static final LocalDateTime dateTime = LocalDateTime.now();

    @Transactional
    public SuccessCreated saveAuthor(
            AuthorCreateDto authorCreateDto
    ) {
        utilsMethods.isExistsByEmail(authorCreateDto.email());

        var savedAuthor = authorRepository.save(
                authorMapper.toEntity(authorCreateDto, passwordEncoder.encode(authorCreateDto.password()))
        );

        eventPublisher.publishEvent(
                new EmailEvent(
                        this,
                        "Sending message for email",
                        savedAuthor.getEmail()
                )
        );

        return successCreatedMapper.toSuccessCreated(
                savedAuthor.toString(),
                ConstantMessages.USER_SUCCESS_CREATED.getDescription(),
                dateTime.toString()
        );
    }
}
