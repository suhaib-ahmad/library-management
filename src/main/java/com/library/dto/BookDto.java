package com.library.dto;

import com.library.entity.Author;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class BookDto {

  private Long id;

  @NotBlank
  @Size(max = 100, message = "Name cannot be more than 100 characters")
  private String name;

  @NotBlank
  @Size(max = 200, message = "Description cannot be more than 200 characters")
  private String description;

  private Set<Author> authors;
}
