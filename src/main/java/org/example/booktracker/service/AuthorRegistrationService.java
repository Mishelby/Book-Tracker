package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.Author.AuthorCreateDto;
import org.example.booktracker.mapper.AuthorMapper;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.repository.AuthorRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.SuccessCreated;
import org.example.booktracker.utils.UtilsMethods;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthorRegistrationService {
    private final AuthorRepository authorRepository;
    private final SuccessCreatedMapper successCreatedMapper;
    private final UtilsMethods utilsMethods;
    private final AuthorMapper authorMapper;

    // static final values
    private static final LocalDateTime dateTime = LocalDateTime.now();

    @Transactional
    public SuccessCreated saveAuthor(
            AuthorCreateDto authorCreateDto
    ) {
        utilsMethods.isExistsByEmail(authorCreateDto.email());

        var savedAuthor = authorRepository.save(
                authorMapper.toEntity(authorCreateDto)
        );

        return successCreatedMapper.toSuccessCreated(
                savedAuthor.toString(),
                ConstantMessages.USER_SUCCESS_CREATED.getDescription(),
                dateTime.toString()
        );
    }
}
