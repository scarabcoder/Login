package com.scarabcoder.test;

import com.google.common.base.Charsets;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

/**
 * Created by Anastasia on 7/8/17.
 */
public class Main {

    public static void main(String[] args){
        String password = "Testing";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        UUID offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + "YourMom").getBytes(Charsets.UTF_8));
        System.out.println(offlineUUID.toString());


    }

}
