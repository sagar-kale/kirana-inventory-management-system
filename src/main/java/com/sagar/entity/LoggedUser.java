package com.sagar.entity;

import lombok.Data;

@Data
public class LoggedUser {
    private boolean isLogged;
    private User loggedInUser;
}
