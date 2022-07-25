package com.example.rumblefishsimpletask.service;

import com.example.rumblefishsimpletask.application.NameDto;
import com.example.rumblefishsimpletask.application.NameNotFoundException;
import com.example.rumblefishsimpletask.domain.Name;
import com.example.rumblefishsimpletask.repo.Repo;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
class NameServiceImpl implements NameService {
    private final Repo<Name> nameRepo;

    NameServiceImpl(Repo<Name> nameRepo) {
        this.nameRepo = nameRepo;
    }

    @Override
    public NameDto save(NameDto nameDto) {
        log.debug("Saving nameDto: " + nameDto);

        val name = new Name(nameDto);
        return nameRepo.save(name)
                .toDto();
    }

    @Override
    public String greet(NameDto nameDto) {
        log.debug("Greeting: " + nameDto);

        val foundName = findNameToGreet(nameDto);
        return createGreeting(foundName);
    }

    private String createGreeting(Name foundName) {
        return "Greeting " + foundName.getName();
    }

    private Name findNameToGreet(NameDto nameDto) {
        val filteredNames = nameRepo.findAll().stream()
                .filter(name -> name.isSameAs(nameDto))
                .collect(Collectors.toSet());

        return getName(filteredNames, nameDto);
    }

    private Name getName(Set<Name> found, NameDto nameDto) {
        if (found.size() < 1) {
            log.debug("Cannot greet user: " + nameDto + " doesnt exist in database");
            throw new NameNotFoundException("Cannot greet: " + nameDto + ". Name doesn't exists in system");
        }

        return found.iterator().next();
    }
}
