package com.library.controller;

import static com.library.controller.util.LibraryConstants.API_BASE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.library.dto.AuthorDto;
import com.library.service.AuthorService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = API_BASE + "/authors", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorService authorService;

  @GetMapping(value = "/{authorId}")
  public ResponseEntity<AuthorDto> getById(@PathVariable Long authorId) {
    log.info("[REQ-GET_ID]: author.id:{}", authorId);
    return authorService.getAuthorById(authorId)
        .map(authorDto -> ResponseEntity.ok(authorDto))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<Set<AuthorDto>> getAllById() {
    log.info("[REQ-GET_ALL]:");
    Set<AuthorDto> authorDtos = authorService.getAllAuthors();
    return ResponseEntity.ok(authorDtos);
  }

  @PostMapping
  public ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid AuthorDto authorDto) {
    authorDto.setId(0L);//to avoid dedup

    log.info("[REQ-POST]: {}", authorDto);
    return authorService.save(authorDto)
        .map(savedAuthorDto ->  ResponseEntity.status(CREATED).body(savedAuthorDto))
        .orElse(ResponseEntity.badRequest().build());
  }

  @PutMapping(value = "/{authorId}")
  public ResponseEntity<AuthorDto> updateAuthor(@RequestBody @Valid AuthorDto authorDto, @PathVariable Long authorId) {

    authorDto.setId(authorId);
    log.info("[REQ-PUT]: {}", authorDto);
    return authorService.update(authorDto)
        .map(savedAuthorDto ->  ResponseEntity.ok(savedAuthorDto))
        .orElse(ResponseEntity.badRequest().build());
  }

  @DeleteMapping(value = "/{authorId}")
  public ResponseEntity deleteById(@PathVariable Long authorId) {

    log.info("[REQ-DELETE]: author.id:{}", authorId);
    return authorService.deleteAuthorById(authorId)?
        ResponseEntity.ok().build():
        ResponseEntity.notFound().build();
  }
}
