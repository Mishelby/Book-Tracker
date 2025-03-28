package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.book.BookCreateDto;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.repository.BookMapper;
import org.example.booktracker.repository.BookRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.SuccessCreated;
import org.example.booktracker.utils.UtilsMethods;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final SuccessCreatedMapper successCreatedMapper;
    private final BookMapper bookMapper;

    // Utils
    private final UtilsMethods utilsMethods;
    private final UUID uuid;

    @Transactional
    public SuccessCreated saveBook(
            BookCreateDto bookDto
    ) {
        utilsMethods.isExistsByNameAndDescription(
                bookDto.name(),
                bookDto.description()
        );

        var savedBook = bookRepository.save(
                bookMapper.toEntity(bookDto, uuid.toString())
        );

        return successCreatedMapper.toSuccessCreated(
                savedBook.toString(),
                ConstantMessages.BOOK_SUCCESS_CREATED.getDescription(),
                LocalDateTime.now().toString()
        );
    }
}
