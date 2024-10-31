package com.openclassrooms.ms_api_gateway.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dbuser" )
public class DBUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String role;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
