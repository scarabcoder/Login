package com.scarabcoder.login.bungeecord;

import java.util.HashMap;
import java.util.UUID;

/**
 * LoginManager, should only be used by BungeeCord proxy plugins!
 */
public class LoginManager {

    private HashMap<UUID, Boolean> users;


    protected LoginManager(){
        users = new HashMap<UUID, Boolean>();
    }



    public void setLoggedIn(UUID id, boolean loggedIn){
        users.put(id, loggedIn);
    }

    public boolean isLoggedIn(UUID id){
        return users.get(id);
    }

    public void removeEntry(UUID id){
        users.remove(id);
    }



}
