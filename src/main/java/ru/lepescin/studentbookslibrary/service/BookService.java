package ru.lepescin.studentbookslibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lepescin.studentbookslibrary.model.Book;
import ru.lepescin.studentbookslibrary.repository.AuthorRepository;
import ru.lepescin.studentbookslibrary.repository.BookRepository;

import java.util.List;

import static ru.lepescin.studentbookslibrary.util.ValidationUtil.checkNotFoundWithId;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void delete(int id) {
        checkNotFoundWithId(bookRepository.delete(id) != 0, id);
    }

    public Book get(int id) {
        return checkNotFoundWithId(bookRepository.findById(id).orElse(null), id);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    protected Book save(Book book) {
        Assert.notNull(book, "book must not be null");
        if (!book.isNew() && get(book.getId()) == null) {
            return null;
        }
        return bookRepository.save(book);
    }

    public Book create(Book book) {
        return save(book);
    }

    public void update(Book book) {
        save(book);
    }

}
