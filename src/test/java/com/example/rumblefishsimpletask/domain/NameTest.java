package com.example.rumblefishsimpletask.domain;

import com.example.rumblefishsimpletask.application.NameDto;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameTest {

    @Test
    void nameSuccessfullyCreated() {
        //given
        val name = "Jan";
        val nameDto = new NameDto(name);

        //when
        val createdName = new Name(nameDto);

        //then
        assertThat(createdName.getName()).isEqualTo(name);
    }

    @Test
    void nameNotCreated_givenBlankString() {
        //given
        val blankName = "";
        val nameDto = new NameDto(blankName);

        //when
        assertThrows(IllegalArgumentException.class, () -> new Name(nameDto));
    }

    @Test
    void nameNotCreated_givenStringContainsNonLetterChars() {
        //given
        val nonLetterChars = "G3org3";
        val nameDto = new NameDto(nonLetterChars);

        //when
        assertThrows(IllegalArgumentException.class, () -> new Name(nameDto));
    }

    @Test
    void nameNotCreated_givenStringLeadingCharNonUppercase() {
        //given
        val leadingCharSmallLetterName = "jan";
        val nameDto = new NameDto(leadingCharSmallLetterName);

        //when
        assertThrows(IllegalArgumentException.class, () -> new Name(nameDto));
    }

    @Test
    void toDto() {
        //given
        val nameValue = "SomeName";
        val nameDto = new NameDto(nameValue);

        val name = new Name(nameDto);

        //when
        val dtoFromName = name.toDto();

        //then
        assertThat(dtoFromName.name()).isEqualTo(name.getName());
    }

    @Test
    void isSameAs() {
        //given
        val nameValue = "SomeName";
        val nameDto = new NameDto(nameValue);

        //when
        val name = new Name(nameDto);

        //then
        assertThat(name.isSameAs(nameDto)).isTrue();
    }

    @Test
    void isSameAs_differentNames() {
        //given
        val nameValue1 = "SomeName";
        val nameDto1 = new NameDto(nameValue1);

        val nameValue2 = "DiffName";
        val nameDto2 = new NameDto(nameValue2);

        //when
        val nameFromDto1 = new Name(nameDto1);

        //then
        assertThat(nameFromDto1.isSameAs(nameDto2)).isFalse();
    }
}