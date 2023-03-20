package com.github.hamideh6182.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdService {

    public String generateId() {

        return UUID.randomUUID().toString();
    }

}
