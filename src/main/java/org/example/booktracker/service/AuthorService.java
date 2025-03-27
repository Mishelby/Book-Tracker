package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.AuthorBook.AuthorBookEntity;
import org.example.booktracker.exception.AuthorNotFoundException;
import org.example.booktracker.exception.BookNotFoundException;
import org.example.booktracker.mapper.AuthorBookMapper;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.repository.AuthorRepository;
import org.example.booktracker.repository.BookRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.SuccessCreated;
import org.example.booktracker.utils.UtilsMethods;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorBookMapper authorBookMapper;

    // Utils
    private final UtilsMethods utilsMethods;
    private final SuccessCreatedMapper successCreatedMapper;

    @Transactional
    public SuccessCreated addBookForAuthor(
            Long authorId,
            Long bookId
    ) {
        utilsMethods.isExistsByAuthorAndBookId(authorId, bookId);

        var authorEntity = authorRepository.findById(authorId).orElseThrow(
                () -> new AuthorNotFoundException("Author with id = %s not found".formatted(authorId))
        );
        var bookEntity = bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException("Book with id = %s not found".formatted(bookId))
        );

        var books = authorEntity.getBooks();
        books.add(authorBookMapper.toEntity(authorEntity, bookEntity));

        return successCreatedMapper.toSuccessCreated(
                String.valueOf(books.size()),
                ConstantMessages.BOOK_SUCCESS_CREATED.getDescription(),
                LocalDateTime.now().toString()
        );
    }
}
