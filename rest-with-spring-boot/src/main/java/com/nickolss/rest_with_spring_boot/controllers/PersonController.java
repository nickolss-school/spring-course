package com.nickolss.rest_with_spring_boot.controllers;

import com.nickolss.rest_with_spring_boot.mapper.ObjectMapper;
import com.nickolss.rest_with_spring_boot.model.dto.v1.PersonDto;
import com.nickolss.rest_with_spring_boot.model.dto.v2.PersonDtoV2;
import com.nickolss.rest_with_spring_boot.services.PersonService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {
    @Autowired
    private PersonService personService;
    private ObjectMapper mapper;

    /* É importante o produces e consumes para a documentação do swagger */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public List<PersonDto> findAll() {
        return personService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public PersonDto findById(@PathVariable("id") Long id) {
        var person = personService.findById(id);
        person.setBirthDay(new Date());
        person.setPhoneNumber("+55 11 91234-5678");
        return person;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    public PersonDto create(@RequestBody PersonDto person) {
        return personService.create(person);
    }

//    @PostMapping(
//            value = "/v2",
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE
//    )
//    public PersonDtoV2 createV2(@RequestBody PersonDtoV2 person) {
//        return personService.createV2(person);
//    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    public PersonDto update(@RequestBody PersonDto person) {
        return personService.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
