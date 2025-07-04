package com.nickolss.rest_with_spring_boot.services;

import com.nickolss.rest_with_spring_boot.controllers.PersonController;
import com.nickolss.rest_with_spring_boot.exception.ResourceNotFoundException;
import com.nickolss.rest_with_spring_boot.model.Person;
import com.nickolss.rest_with_spring_boot.model.dto.v1.PersonDto;
import com.nickolss.rest_with_spring_boot.repositories.PersonRepository;

import static com.nickolss.rest_with_spring_boot.mapper.ObjectMapper.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Service
public class PersonService {
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public PersonDto findById(Long id) {
        logger.info("Finding one person...");

        var dto = parseObject(personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id.")), PersonDto.class);

        addHateosLinks(dto);

        return dto;
    }

    public List<PersonDto> findAll() {
        logger.info("Finding all people...");
        var persons = parseListObjects(personRepository.findAll(), PersonDto.class);
        persons.forEach(this::addHateosLinks);
        return persons;
    }

    public PersonDto create(PersonDto personDto) {
        logger.info("Creating person...");

        var dto = parseObject(personRepository.save(parseObject(personDto, Person.class)), PersonDto.class);
        addHateosLinks(dto);

        return dto;
    }

//    public PersonDtoV2 createV2(PersonDtoV2 personDto) {
//        logger.info("Creating person...");
//
//        return parseObject(personRepository.save(parseObject(personDto, Person.class)), PersonDtoV2.class);
//    }


    public PersonDto update(PersonDto person) {
        logger.info("Updating person...");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(personRepository.save(entity), PersonDto.class);
        addHateosLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting person...");
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        personRepository.delete(entity);
    }

    private void addHateosLinks(PersonDto dto) {
        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(PersonController.class)
                                        .findById(dto.getId())
                        )
                        .withSelfRel()
                        .withType("GET")
        );
        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(PersonController.class)
                                        .delete(dto.getId())
                        )
                        .withRel("delete")
                        .withType("DELETE")
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(PersonController.class)
                                        .findAll()
                        )
                        .withRel("find_all")
                        .withType("GET")
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(PersonController.class)
                                        .create(dto)
                        )
                        .withRel("create")
                        .withType("POST")
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(PersonController.class)
                                        .update(dto)
                        )
                        .withRel("update")
                        .withType("PUT`")
        );
    }
}
