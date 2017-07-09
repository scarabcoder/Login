package com.scarabcoder.login.spigot.manager;


import java.util.HashMap;
import java.util.UUID;

/**
 * Simple session data storage
 */
public class LoginManager {

    private HashMap<UUID, Boolean> loggedIn = new HashMap<UUID, Boolean>();


    public void setLoggedIn(UUID id, boolean loggedIn){
        this.loggedIn.put(id, loggedIn);
    }

    public boolean isLoggedIn(UUID id){
        return this.loggedIn.get(id);
    }

}
