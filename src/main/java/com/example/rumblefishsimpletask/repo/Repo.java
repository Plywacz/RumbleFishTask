package com.example.rumblefishsimpletask.repo;

import java.util.Set;

public interface Repo<T>{
    T save(T name);

    Set<T> findAll();
}
