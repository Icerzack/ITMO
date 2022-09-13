package com.lab4.DataClasses;


import lombok.Data;
import lombok.NonNull;

@Data
public class UserData {
    @NonNull
    private String username;

    @NonNull
    private String password;
}