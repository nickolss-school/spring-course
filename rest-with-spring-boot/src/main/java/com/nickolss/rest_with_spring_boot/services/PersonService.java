package com.nickolss.rest_with_spring_boot.services;

import com.nickolss.rest_with_spring_boot.exception.ResourceNotFoundException;
import com.nickolss.rest_with_spring_boot.model.Person;
import com.nickolss.rest_with_spring_boot.model.dto.PersonDto;
import com.nickolss.rest_with_spring_boot.repositories.PersonRepository;

import static com.nickolss.rest_with_spring_boot.mapper.ObjectMapper.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public PersonDto findById(Long id) {
        logger.info("Finding one person...");
        return parseObject(personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id.")), PersonDto.class);
    }

    public List<PersonDto> findAll() {
        logger.info("Finding all people...");
        return parseListObjects(personRepository.findAll(), PersonDto.class);
    }

    public PersonDto create(PersonDto personDto) {
        logger.info("Creating person...");

        return parseObject(personRepository.save(parseObject(personDto, Person.class)), PersonDto.class);
    }

    public PersonDto update(PersonDto person) {
        logger.info("Updating person...");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(personRepository.save(entity), PersonDto.class);
    }

    public void delete(Long id) {
        logger.info("Deleting person...");
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        personRepository.delete(entity);
    }
}
