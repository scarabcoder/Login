package com.scarabcoder.login.bungeecord.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.scarabcoder.login.bungeecord.LoginBungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Anastasia on 7/8/17.
 */
public class PluginMessageListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event){




        if(!event.getTag().equals("Login"))
            return;
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));

        try {
            String sub = in.readUTF();


            switch(sub){
                case "IsLoggedIn":
                    UUID user = UUID.fromString(in.readUTF());
                    Server server = ProxyServer.getInstance().getPlayer(user).getServer();
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();

                    out.writeUTF("IsLoggedIn");
                    out.writeUTF(user.toString());

                    out.writeBoolean(LoginBungeeCord.getLoginManager().isLoggedIn(user));

                    server.sendData("Login", out.toByteArray());



                    break;
                case "SetLoggedIn":
                    user = UUID.fromString(in.readUTF());
                    LoginBungeeCord.getLoginManager().setLoggedIn(user, in.readBoolean());
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
