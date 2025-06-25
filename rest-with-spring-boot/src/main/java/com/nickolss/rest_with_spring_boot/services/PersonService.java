package com.nickolss.rest_with_spring_boot.services;

import com.nickolss.rest_with_spring_boot.exception.ResourceNotFoundException;
import com.nickolss.rest_with_spring_boot.model.Person;
import com.nickolss.rest_with_spring_boot.repositories.PersonRepository;
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

    public Person findById(Long id) {
        logger.info("Finding one person...");
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
    }

    public List<Person> findAll() {
        logger.info("Finding all people...");
        return personRepository.findAll();
    }

    public Person create(Person person) {
        logger.info("Creating person...");

        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating person...");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deleting person...");
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        personRepository.delete(entity);
    }
}
