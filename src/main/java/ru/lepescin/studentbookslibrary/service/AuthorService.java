package ru.lepescin.studentbookslibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lepescin.studentbookslibrary.model.Author;
import ru.lepescin.studentbookslibrary.repository.AuthorRepository;

import java.util.List;

import static ru.lepescin.studentbookslibrary.util.ValidationUtil.checkNotFoundWithId;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Author get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Author> getAll() {
        return repository.findAll();
    }

    @Transactional
    protected Author save(Author author) {
        Assert.notNull(author, "author must not be null");
        return checkNotFoundWithId(repository.save(author), author.getId());
    }

    public Author create(Author author) {
        return save(author);
    }

    public void update(Author author) {
        save(author);
    }
}
