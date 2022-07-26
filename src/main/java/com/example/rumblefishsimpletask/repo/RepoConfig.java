package com.example.rumblefishsimpletask.repo;

import com.example.rumblefishsimpletask.domain.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
class RepoConfig {
    @Bean
    Set<Name> nameData() {
        return new HashSet<>();
    }
}
