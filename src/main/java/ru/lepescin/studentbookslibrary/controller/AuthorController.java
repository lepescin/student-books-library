package ru.lepescin.studentbookslibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lepescin.studentbookslibrary.model.Author;
import ru.lepescin.studentbookslibrary.service.AuthorService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.lepescin.studentbookslibrary.util.ValidationUtil.assureIdConsistent;
import static ru.lepescin.studentbookslibrary.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AuthorController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AuthorController {
    static final String REST_URL = "/authors";

    @Autowired
    private AuthorService authorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> create(@Valid @RequestBody Author author) {
        log.info("create {}", author);
        checkNew(author);
        Author created = authorService.create(author);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete author with id={}", id);
        authorService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Author author,
                       @PathVariable int id) {
        assureIdConsistent(author, id);
        log.info("update {} with id={}", author, id);
        authorService.update(author);
    }

    @GetMapping
    public List<Author> getAll() {
        log.info("get all authors");
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public Author get(@PathVariable int id) {
        log.info("get author {} with books", id);
        return authorService.get(id);
    }
}
