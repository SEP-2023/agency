package com.project.agencija.dto;

import lombok.*;


@Data
public class ClientRequestDTO{

    public String name;
    public String surname;
    public String email;
    public String password;
    public String role;
    public String pin;
}
