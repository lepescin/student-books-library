package ru.lepescin.studentbookslibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lepescin.studentbookslibrary.model.Book;
import ru.lepescin.studentbookslibrary.service.BookService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.lepescin.studentbookslibrary.util.ValidationUtil.assureIdConsistent;
import static ru.lepescin.studentbookslibrary.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = BookController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BookController {
    static final String REST_URL = "/books";

    @Autowired
    private BookService bookService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
        log.info("create {}", book);
        checkNew(book);
        Book created = bookService.create(book);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete book with id={}", id);
        bookService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Book book,
                       @PathVariable int id) {
        assureIdConsistent(book, id);
        log.info("update {} with id={}", book, id);
        bookService.update(book);
    }

    @GetMapping
    public List<Book> getAll() {
        log.info("get all books");
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable int id) {
        log.info("get book {}", id);
        return bookService.get(id);
    }
}
