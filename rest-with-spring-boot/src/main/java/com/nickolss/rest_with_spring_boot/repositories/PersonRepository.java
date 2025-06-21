package com.nickolss.rest_with_spring_boot.repositories;

import com.nickolss.rest_with_spring_boot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
