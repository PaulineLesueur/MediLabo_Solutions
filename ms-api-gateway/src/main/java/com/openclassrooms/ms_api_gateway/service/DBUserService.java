package com.openclassrooms.ms_api_gateway.service;

import com.openclassrooms.ms_api_gateway.DTO.AuthCredentials;
import com.openclassrooms.ms_api_gateway.model.DBUser;
import com.openclassrooms.ms_api_gateway.repository.DBUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DBUserService {
    @Autowired
    private DBUserRepository dbUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DBUser login(AuthCredentials authCredentials) {
        Optional<DBUser> user = dbUserRepository.findByUsername(authCredentials.getUsername());

        if(user.isPresent()) {
            DBUser userFound = user.get();
            if(passwordEncoder.matches(authCredentials.getPassword(), userFound.getPassword())) {
                return userFound;
            }
            throw new BadCredentialsException("Wrong password");
        }
        throw new UsernameNotFoundException(authCredentials.getUsername() + " isn't registered yet");

    }
}
