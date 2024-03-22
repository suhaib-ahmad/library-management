package com.library.service;

import com.library.dto.AuthorDto;
import java.util.Optional;
import java.util.Set;

public interface AuthorService {

  Set<AuthorDto> getAllAuthors();

  Optional<AuthorDto> getAuthorById(Long authorId);

  Optional<AuthorDto> save(AuthorDto authorDto);

  Optional<AuthorDto> update(AuthorDto authorDto);

  boolean deleteAuthorById(Long authorId);
}
