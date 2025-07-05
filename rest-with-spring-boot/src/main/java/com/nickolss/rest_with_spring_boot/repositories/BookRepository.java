package com.nickolss.rest_with_spring_boot.repositories;

import com.nickolss.rest_with_spring_boot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
