package com.example.rumblefishsimpletask.conttroller;

import com.example.rumblefishsimpletask.application.NameAlreadyExistsException;
import com.example.rumblefishsimpletask.application.NameDto;
import com.example.rumblefishsimpletask.application.NameNotFoundException;
import com.example.rumblefishsimpletask.service.NameService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@RestController
@RequestMapping("/name")
public
class NameController {
    private final NameService nameService;

    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public NameDto save(@RequestBody NameDto nameDto) {
        log.debug("save name: " + nameDto + ". requested");

        try {
            return nameService.save(nameDto);
        } catch (NameAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/{greet}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String greet(@PathVariable NameDto greet) {
        log.debug("greet name: " + greet + ". requested");

        try {
            return nameService.greet(greet);
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
