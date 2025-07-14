package com.nickolss.rest_with_spring_boot.services;

import com.nickolss.rest_with_spring_boot.controllers.PersonController;
import com.nickolss.rest_with_spring_boot.exception.ResourceNotFoundException;
import com.nickolss.rest_with_spring_boot.model.Person;
import com.nickolss.rest_with_spring_boot.model.dto.v1.PersonDto;
import com.nickolss.rest_with_spring_boot.repositories.PersonRepository;
import static com.nickolss.rest_with_spring_boot.mapper.ObjectMapper.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

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

    public Page<PersonDto> findAll(Pageable pageable) {
        logger.info("Finding all people...");

        var people = personRepository.findAll(pageable);

        var peopleWithLinks = people.map(person -> {
                var dto = parseObject(person, PersonDto.class);
                addHateosLinks(dto);
                return dto;
        });

        return peopleWithLinks;
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

    @Transactional
    public PersonDto disablePerson(Long id) {
        logger.info("Disabling one person...");
        personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        personRepository.disablePerson(id);
        var entity = personRepository.findById(id).get();
        var dto = parseObject(entity, PersonDto.class);
        addHateosLinks(dto);
        return dto;
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
                                        .findAll(0, 12)
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
                        .withType("PUT")
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(PersonController.class)
                                        .disablePerson(dto.getId())
                        )
                        .withRel("disable")
                        .withType("PATCH")
        );
    }
}
