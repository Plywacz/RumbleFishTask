package com.example.rumblefishsimpletask.domain;

import com.example.rumblefishsimpletask.application.NameDto;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Log4j2
@Value
public class Name {
    String name;

    private Name(String name) {
        log.debug("instantiating name. Given name: " + name);

        ensureIsNotBlank(name);
        ensureContainsOnlyLetters(name);
        ensureStartsWithUppercaseLetter(name);

        this.name = name;
    }

    public Name(NameDto nameDto) {
        this(nameDto.name());
    }

    public NameDto toDto() {
        return new NameDto(this.name);
    }

    public boolean isSameAs(NameDto nameDto) {
        return this.name.equals(nameDto.name());
    }

    private void ensureIsNotBlank(String name) {
        if (isBlank(name)) {
            log.debug("Cannot instantiate name obj with: " + name + ". Blank String given");
            throw new IllegalArgumentException("Provided name is blank");
        }
    }

    private void ensureContainsOnlyLetters(String name) {
        val allCharsAreLetters = name.chars().allMatch(Character::isLetter);

        if (!allCharsAreLetters) {
            log.debug("Cannot instantiate name obj with: " + name + ". Given string with non letter chars");
            throw new IllegalArgumentException("Provided name contains non letter characters");
        }
    }

    private void ensureStartsWithUppercaseLetter(String name) {
        if (!Character.isUpperCase(name.charAt(0))) {
            log.debug("Cannot instantiate name obj with: " + name + ". Given string leading char is not uppercase letter");
            throw new IllegalArgumentException("Provided starts with lower case letter");
        }
    }
}
