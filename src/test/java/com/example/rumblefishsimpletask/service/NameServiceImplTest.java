package com.example.rumblefishsimpletask.service;

import com.example.rumblefishsimpletask.application.NameDto;
import com.example.rumblefishsimpletask.application.NameNotFoundException;
import com.example.rumblefishsimpletask.domain.Name;
import com.example.rumblefishsimpletask.repo.Repo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NameServiceImplTest {
    private Repo<Name> nameRepo;
    private NameService nameService;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        nameRepo = (Repo<Name>) mock(Repo.class);
        nameService = new NameServiceImpl(nameRepo);
    }

    @Test
    void save() {
        //given
        val toSaveNameDto = new NameDto("Name");

        val savedNameDto = new NameDto("Name");
        val savedName = new Name(savedNameDto);

        when(nameRepo.save(any(Name.class))).thenReturn(savedName);

        //when
        val result = nameService.save(toSaveNameDto);

        //then
        verify(nameRepo).save(any(Name.class));
        assertThat(result.name()).isEqualTo(toSaveNameDto.name());
    }

    @Test
    void greet() {
        //given
        val toGreetNameDto = new NameDto("Name");

        val savedName1 = new Name(new NameDto("Name"));
        val savedName2 = new Name(new NameDto("SecName"));
        val savedName3 = new Name(new NameDto("ThName"));

        val savedNames = Set.of(savedName1, savedName2, savedName3);

        when(nameRepo.findAll()).thenReturn(savedNames);

        //when
        val createdGreeting = nameService.greet(toGreetNameDto);

        //then
        verify(nameRepo).findAll();
        assertThat(createdGreeting).isEqualTo("Greeting Name");
    }

    @Test
    void greet_nameNotDefined() {
        //given
        val toGreetNameDto = new NameDto("Name");

        val savedName1 = new Name(new NameDto("FName"));
        val savedName2 = new Name(new NameDto("SCName"));
        val savedName3 = new Name(new NameDto("ThName"));

        val savedNames = Set.of(savedName1, savedName2, savedName3);

        when(nameRepo.findAll()).thenReturn(savedNames);

        //when
        val exception = assertThrows(NameNotFoundException.class, () -> nameService.greet(toGreetNameDto));

        //then
        verify(nameRepo).findAll();
        assertThat(exception.getMessage()).isNotEmpty();
    }
}