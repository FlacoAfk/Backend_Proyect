package com.example.podcast.solicitudes;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String passwordConfirm;
    private String password;
    private String document;
    private String phone;
    private String address;
}
