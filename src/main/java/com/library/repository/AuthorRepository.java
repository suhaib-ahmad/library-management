package com.library.repository;

import com.library.entity.Author;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long>{

  boolean existsByName(String name);
}
