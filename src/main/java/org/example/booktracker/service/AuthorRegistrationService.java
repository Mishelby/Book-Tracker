package org.example.booktracker.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.authorRating.AuthorRatingEntity;
import org.example.booktracker.evenListener.EmailEvent;
import org.example.booktracker.domain.author.AuthorCreateDto;
import org.example.booktracker.mapper.AuthorMapper;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.repository.AuthorRatingRepository;
import org.example.booktracker.repository.AuthorRepository;
import org.example.booktracker.repository.CityRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.DefaultValues;
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
    // Repositories
    private final AuthorRepository authorRepository;
    private final CityRepository cityRepository;
    private final AuthorRatingRepository authorRatingRepository;

    // Utils
    private final UtilsMethods utilsMethods;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    // Mappers
    private final AuthorMapper authorMapper;
    private final SuccessCreatedMapper successCreatedMapper;

    // Static final values
    private static final LocalDateTime dateTime = LocalDateTime.now();

    @Transactional
    public SuccessCreated saveAuthor(
            AuthorCreateDto authorCreateDto
    ) {
        utilsMethods.isExistsByEmail(authorCreateDto.email());
        var cityEntity = cityRepository.findByName(authorCreateDto.cityName()).orElseThrow(
                () -> new EntityNotFoundException("City with name = %s not found!"
                        .formatted(authorCreateDto.cityName()))
        );

        var savedAuthor = authorRepository.save(authorMapper.toEntity(
                authorCreateDto,
                passwordEncoder.encode(authorCreateDto.password()),
                cityEntity
        ));

        authorRatingRepository.save(
                new AuthorRatingEntity(savedAuthor.getId(),
                        DefaultValues.getDefaultRating())
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
