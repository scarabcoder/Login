package com.scarabcoder.login;

import java.util.UUID;

/**
 * Simple data object for storing cached data from the database
 */
public class CachedPlayerData {

    private UUID id;

    private String username;

    private String hashedPassword;
    private boolean premium;

    public CachedPlayerData(UUID id, String username, String hashedPassword, boolean premium){
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.premium = premium;
    }

    public UUID getID(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getHashedPassword(){
        return this.hashedPassword;
    }

    public boolean isPremiumAccount(){
        return this.premium;
    }

    public void setHashedPassword(String password){
        this.hashedPassword = password;
    }

}
