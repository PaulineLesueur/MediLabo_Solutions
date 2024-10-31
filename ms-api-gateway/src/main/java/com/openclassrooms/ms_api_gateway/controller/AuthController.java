package com.openclassrooms.ms_api_gateway.controller;

import com.openclassrooms.ms_api_gateway.DTO.AuthCredentials;
import com.openclassrooms.ms_api_gateway.model.DBUser;
import com.openclassrooms.ms_api_gateway.service.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private DBUserService dbUserService;

    @PostMapping("/login")
    public ResponseEntity<DBUser> login(@RequestBody AuthCredentials authCredentials) {
        DBUser user = dbUserService.login(authCredentials);
        return ResponseEntity.ok(user);
    }

}
