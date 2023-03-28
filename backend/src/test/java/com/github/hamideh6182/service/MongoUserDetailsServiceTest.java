package com.github.hamideh6182.service;

import com.github.hamideh6182.model.MongoUser;
import com.github.hamideh6182.model.MongoUserRequest;
import com.github.hamideh6182.model.MongoUserResponse;
import com.github.hamideh6182.repository.MongoUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MongoUserDetailsServiceTest {
    MongoUserRepository mongoUserRepository;
    PasswordEncoder passwordEncoder;
    IdService idService;
    MongoUser mongoUser;
    MongoUserDetailsService mongoUserDetailsService;

    @BeforeEach
    void setUp() {
        mongoUserRepository = mock(MongoUserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        idService = mock(IdService.class);
        mongoUserDetailsService = new MongoUserDetailsService(mongoUserRepository, passwordEncoder, idService);
        mongoUser = new MongoUser("1", "username", "123", "BASIC");
    }

    @Test
    void loadUserByUsername() {
        //GIVEN
        when(mongoUserRepository.findByUsername("username")).thenReturn(Optional.of(mongoUser));
        //WHEN
        UserDetails actual = mongoUserDetailsService.loadUserByUsername("username");
        UserDetails expected = new User(mongoUser.username(), mongoUser.password(),
                List.of(new SimpleGrantedAuthority(("ROLE_" + mongoUser.role()))));
        //THEN
        verify(mongoUserRepository).findByUsername("username");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void signup() {
        when(passwordEncoder.encode(mongoUser.password())).thenReturn(mongoUser.password());
        when(idService.generateId()).thenReturn(mongoUser.id());
        MongoUserRequest mongoUserRequest = new MongoUserRequest(mongoUser.username(), mongoUser.password());
        when(mongoUserRepository.save(mongoUser)).thenReturn(mongoUser);

        MongoUserResponse actual = mongoUserDetailsService.signup(mongoUserRequest);
        MongoUserResponse expected = new MongoUserResponse(mongoUser.id(), mongoUser.username(), mongoUser.role());

        verify(passwordEncoder).encode(mongoUser.password());
        verify(idService).generateId();
        verify(mongoUserRepository).save(mongoUser);
        Assertions.assertEquals(expected, actual);
    }
}