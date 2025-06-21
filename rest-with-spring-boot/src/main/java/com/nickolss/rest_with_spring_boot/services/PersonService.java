package com.nickolss.rest_with_spring_boot.services;

import com.nickolss.rest_with_spring_boot.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        person.setFirstName("Pessoa");
        person.setLastName("Maia");
        person.setAddress("Rua aa");
        person.setGender("Male");

        return person;
    }

    public List<Person> findAll() {
        logger.info("Finding all people...");

        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 8; i++) {
            Person person = new Person();
            person.setId(counter.incrementAndGet());
            person.setFirstName("Pessoa " + i);
            person.setLastName("Sobrenome " + i);
            person.setAddress("Rua " + i);
            person.setGender("male " + i);
            persons.add(person);
        }

        return persons;
    }
}
