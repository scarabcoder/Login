package com.scarabcoder.login.spigot.listener;

import com.scarabcoder.login.spigot.LoginSpigot;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Receive session data from the proxy plugin
 */
public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(!channel.equals("Login"))
            return;

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            String sub = in.readUTF();
            switch(sub){
                case "IsLoggedIn":
                    UUID user = UUID.fromString(in.readUTF());
                    boolean isLoggedIn = in.readBoolean();

                    LoginSpigot.getLoginManager().setLoggedIn(user, isLoggedIn);
                    System.out.println(isLoggedIn);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
