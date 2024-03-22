package com.library.service.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import com.library.dto.AuthorDto;
import com.library.dto.BookDto;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
  private final ModelMapper modelMapper;

  @Override
  public Set<AuthorDto> getAllAuthors() {
    return authorRepository.findAll().stream()
        .map(author -> modelMapper.map(author, AuthorDto.class))
        .collect(Collectors.toSet());
  }

  @Override
  public Optional<AuthorDto> getAuthorById(Long authorId) {

    return authorRepository.findById(authorId)
        .map(authorEntity -> modelMapper.map(authorEntity, AuthorDto.class));
  }

  @Override
  public Optional<AuthorDto> save(AuthorDto authorDto) {

    if(authorRepository.existsByName(authorDto.getName())) {
      log.info("[DUPLICATE_AUTHOR]: found duplicate author name:{}", authorDto.getName());
      return Optional.empty();
    }

    Author author = modelMapper.map(authorDto, Author.class);
    author.setBooks(mapBookDtoToEntity(authorDto.getBooks()));
    return ofNullable(authorRepository.save(author))
        .map(authorSaved -> modelMapper.map(authorSaved, AuthorDto.class));
  }

  private Set<Book> mapBookDtoToEntity(Set<BookDto> books) {

    if(isNull(books))
      return null;

    return books.stream()
        .map(bookDto -> modelMapper.map(bookDto, Book.class))
        .collect(Collectors.toSet());
  }

  @Override
  public Optional<AuthorDto> update(AuthorDto authorDto) {

    Optional<Author> existingAuthor = authorRepository.findById(authorDto.getId());
    if(existingAuthor.isEmpty()) {
      log.info("AUTHOR_NOT_FOUND id:{}", authorDto.getId());
      return Optional.empty();
    }

    existingAuthor.ifPresent(author -> {
      authorDto.setName(author.getName());
      authorDto.setDescription(author.getDescription());
      authorDto.setBooks((author.getBooks()).stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toSet()));
    });

    return ofNullable(authorRepository.save(modelMapper.map(authorDto, Author.class)))
        .map(authorSaved -> modelMapper.map(authorSaved, AuthorDto.class));
  }
  @Override
  public boolean deleteAuthorById(Long authorId) {

    if(authorRepository.existsById(authorId)) {
      authorRepository.deleteById(authorId);
      return true;
    }
    return false;
  }

}
