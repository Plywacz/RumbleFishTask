package com.example.rumblefishsimpletask.repo;

import com.example.rumblefishsimpletask.application.NameAlreadyExistsException;
import com.example.rumblefishsimpletask.domain.Name;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.Set;

import static java.util.Collections.unmodifiableSet;

@Log4j2
@Repository
class NameRepo implements Repo<Name> {
    private final Set<Name> data;

    NameRepo(Set<Name> data) {
        this.data = data;
    }

    @Override
    public Name save(Name name) {
        log.debug("Inserting name to db: " + name);

        val added = data.add(name);
        if (!added) {
            log.debug("Cannot insert name to db: " + name + ". Name already in db");
            throw new NameAlreadyExistsException("Given duplicated name");
        }
        return name;
    }

    @Override
    public Set<Name> findAll() {
        log.debug("Retrieving all names from db");

        return unmodifiableSet(data);
    }
}
