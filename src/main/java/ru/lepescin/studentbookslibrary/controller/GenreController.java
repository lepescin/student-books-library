package ru.lepescin.studentbookslibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lepescin.studentbookslibrary.model.Genre;
import ru.lepescin.studentbookslibrary.service.GenreService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.lepescin.studentbookslibrary.util.ValidationUtil.assureIdConsistent;
import static ru.lepescin.studentbookslibrary.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = GenreController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class GenreController {
    static final String REST_URL = "/genres";

    @Autowired
    private GenreService genreService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Genre> create(@Valid @RequestBody Genre genre) {
        log.info("create {}", genre);
        checkNew(genre);
        Genre created = genreService.create(genre);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete genre with id={}", id);
        genreService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Genre genre,
                       @PathVariable int id) {
        assureIdConsistent(genre, id);
        log.info("update {} with id={}", genre, id);
        genreService.update(genre);
    }

    @GetMapping
    public List<Genre> getAll() {
        log.info("get all genres");
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable int id) {
        log.info("get genre {}", id);
        return genreService.get(id);
    }
}
