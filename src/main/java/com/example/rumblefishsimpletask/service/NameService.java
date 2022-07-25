package com.example.rumblefishsimpletask.service;

import com.example.rumblefishsimpletask.application.NameDto;

public interface NameService {
    NameDto save(NameDto name);

    String greet(NameDto name);
}
