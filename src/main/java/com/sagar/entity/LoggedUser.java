package com.sagar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUser {
    private boolean isLogged = false;
    private User loggedInUser;
}
