package com.example.rumblefishsimpletask.repo;

import com.example.rumblefishsimpletask.application.NameAlreadyExistsException;
import com.example.rumblefishsimpletask.application.NameDto;
import com.example.rumblefishsimpletask.domain.Name;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NameRepoTest {
    private Repo<Name> nameRepo;

    @BeforeEach
    void setUp() {
        nameRepo = new NameRepo();
    }

    @Test
    void save_twoItemsAdded() {
        assertThat(nameRepo.findAll().size()).isEqualTo(0);

        //given
        val name1 = new Name(new NameDto("FirstName"));
        val name2 = new Name(new NameDto("SecName"));

        //when
        nameRepo.save(name1);
        nameRepo.save(name2);

        //then
        assertThat(nameRepo.findAll().size()).isEqualTo(2L);
    }

    @Test
    void save_tryingToAddDuplicatedName_ExceptionThrown() {
        assertThat(nameRepo.findAll().size()).isEqualTo(0);

        //given
        val name1 = new Name(new NameDto("Name"));
        val name2 = new Name(new NameDto("Name"));

        //when
        val exception = assertThrows(NameAlreadyExistsException.class, () -> {
            nameRepo.save(name1);
            nameRepo.save(name2);
        });

        //then
        assertThat(exception.getMessage()).isNotBlank();
    }

    @Test
    void findAll() {
        //given
        val name1 = new Name(new NameDto("FirstName"));
        val name2 = new Name(new NameDto("SecName"));

        //when
        nameRepo.save(name1);
        nameRepo.save(name2);

        //then
        val allNames = nameRepo.findAll();
        assertThat(allNames).hasSize(2);
        assertThat(allNames).allSatisfy(name -> assertThat(name.getName()).isNotNull());
    }
}