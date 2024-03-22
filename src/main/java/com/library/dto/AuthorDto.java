package com.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorDto {

  private Long id;

  @NotBlank
  @Size(max = 100, message = "Name cannot be more than 100 characters")
  private String name;

  @NotBlank
  @Size(max = 200, message = "Description cannot be more than 200 characters")
  private String description;

  private Set<BookDto> books;
}
