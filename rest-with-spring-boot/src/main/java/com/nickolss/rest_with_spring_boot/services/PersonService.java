package com.nickolss.rest_with_spring_boot.services;

import com.nickolss.rest_with_spring_boot.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private static final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(String id) {
        logger.info("Finding one person...");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Nickolas");
        person.setLastName("Maia");
        person.setAddress("Rua aa");
        person.setGender("Male");

        return person;
    }
}
