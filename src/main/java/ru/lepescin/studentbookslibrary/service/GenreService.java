package ru.lepescin.studentbookslibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lepescin.studentbookslibrary.model.Author;
import ru.lepescin.studentbookslibrary.model.Genre;
import ru.lepescin.studentbookslibrary.repository.AuthorRepository;
import ru.lepescin.studentbookslibrary.repository.GenreRepository;

import java.util.List;

import static ru.lepescin.studentbookslibrary.util.ValidationUtil.checkNotFoundWithId;

@Service
public class GenreService {
    private final GenreRepository repository;

    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Genre get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Genre> getAll() {
        return repository.findAll();
    }

    @Transactional
    protected Genre save(Genre genre) {
        Assert.notNull(genre, "genre must not be null");
        return checkNotFoundWithId(repository.save(genre), genre.getId());
    }

    public Genre create(Genre genre) {
        return save(genre);
    }

    public void update(Genre genre) {
        save(genre);
    }
}
