package com.nickolss.rest_with_spring_boot.services;

import com.nickolss.rest_with_spring_boot.controllers.BookController;
import com.nickolss.rest_with_spring_boot.controllers.PersonController;
import com.nickolss.rest_with_spring_boot.exception.ResourceNotFoundException;
import com.nickolss.rest_with_spring_boot.model.Book;
import com.nickolss.rest_with_spring_boot.model.Person;
import com.nickolss.rest_with_spring_boot.model.dto.v1.BookDto;
import com.nickolss.rest_with_spring_boot.model.dto.v1.PersonDto;
import com.nickolss.rest_with_spring_boot.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nickolss.rest_with_spring_boot.mapper.ObjectMapper.parseListObjects;
import static com.nickolss.rest_with_spring_boot.mapper.ObjectMapper.parseObject;

@Service
public class BookService {
    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository bookRepository;

    public BookDto findById(Long id) {
        logger.info("Finding one person...");

        var dto = parseObject(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id.")), BookDto.class);

        addHateosLinks(dto);

        return dto;
    }

    public List<BookDto> findAll() {
        logger.info("Finding all people...");
        var persons = parseListObjects(bookRepository.findAll(), BookDto.class);
        persons.forEach(this::addHateosLinks);
        return persons;
    }

    public BookDto create(BookDto BookDto) {
        logger.info("Creating person...");

        var dto = parseObject(bookRepository.save(parseObject(BookDto, Book.class)), BookDto.class);
        addHateosLinks(dto);

        return dto;
    }


    public BookDto update(BookDto book) {
        logger.info("Updating person...");
        Book entity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());

        var dto = parseObject(bookRepository.save(entity), BookDto.class);
        addHateosLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting person...");
        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        bookRepository.delete(entity);
    }

    private void addHateosLinks(BookDto dto) {
        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(BookController.class)
                                        .findById(dto.getId())
                        )
                        .withSelfRel()
                        .withType("GET")
        );
        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(BookController.class)
                                        .delete(dto.getId())
                        )
                        .withRel("delete")
                        .withType("DELETE")
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(BookController.class)
                                        .findAll()
                        )
                        .withRel("find_all")
                        .withType("GET")
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(BookController.class)
                                        .create(dto)
                        )
                        .withRel("create")
                        .withType("POST")
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(BookController.class)
                                        .update(dto)
                        )
                        .withRel("update")
                        .withType("PUT`")
        );
    }
}
