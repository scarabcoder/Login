package com.scarabcoder.login.bungeecord;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Anastasia on 7/8/17.
 */
public class LoginManager {

    private HashMap<UUID, Boolean> users;


    protected LoginManager(){
        users = new HashMap<UUID, Boolean>();
    }

    public void registerNewUser(UUID uuid){
        users.put(uuid, false);
    }

    public void setLoggedIn(UUID id, boolean loggedIn){
        users.put(id, loggedIn);
    }

    public boolean isLoggedIn(UUID id){
        return users.get(id);
    }



}
