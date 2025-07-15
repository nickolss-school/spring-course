package com.nickolss.rest_with_spring_boot.controllers;

import com.nickolss.rest_with_spring_boot.mapper.ObjectMapper;
import com.nickolss.rest_with_spring_boot.model.dto.v1.PersonDto;
import com.nickolss.rest_with_spring_boot.services.PersonService;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for managing people")
// @CrossOrigin(origins = "http://localhost:8080")
public class PersonController {
        @Autowired
        private PersonService personService;
        private ObjectMapper mapper;

        /* É importante o produces e consumes para a documentação do swagger */
        @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE })
        @Operation(summary = "Find all People", tags = "People", responses = {
                        @ApiResponse(description = "Sucess", responseCode = "200", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = PersonDto.class)))
                        }),
                        @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                        @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
        public ResponseEntity<Page<PersonDto>> findAll(
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction) {

                var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
                return ResponseEntity.ok(personService.findAll(pageable));
        }

        @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE })
        @Operation(summary = "Find by id", tags = "People", responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDto.class))),
                        @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
        // @CrossOrigin(origins = "http://localhost:8080")
        public PersonDto findById(@PathVariable("id") Long id) {
                return personService.findById(id);
        }

        @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
                                        MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })
        @Operation(summary = "Create a Person", tags = "People", responses = {
                        @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = PersonDto.class))),
                        @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
        // @CrossOrigin(origins = "http://localhost:8080")
        public PersonDto create(@RequestBody PersonDto person) {
                return personService.create(person);
        }

        // @PostMapping(
        // value = "/v2",
        // produces = MediaType.APPLICATION_JSON_VALUE,
        // consumes = MediaType.APPLICATION_JSON_VALUE
        // )
        // public PersonDtoV2 createV2(@RequestBody PersonDtoV2 person) {
        // return personService.createV2(person);
        // }

        @PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
                                        MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })
        @Operation(summary = "Updating a person", tags = "People", responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDto.class))),
                        @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
        public PersonDto update(@RequestBody PersonDto person) {
                return personService.update(person);
        }

        @PatchMapping(value = "/{id}")
        @Operation(summary = "Disable a Person", tags = "People", responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                        @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
        public ResponseEntity<PersonDto> disablePerson(@PathVariable Long id) {
                return ResponseEntity.ok().body(personService.disablePerson(id));
        }

        @DeleteMapping(value = "/{id}")
        @Operation(summary = "Deleting a Person", tags = "People", responses = {
                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                        @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
        public ResponseEntity<?> delete(@PathVariable Long id) {
                personService.delete(id);
                return ResponseEntity.noContent().build();
        }

}
