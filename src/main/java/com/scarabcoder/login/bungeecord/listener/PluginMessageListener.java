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
 * Custom channel listeners, for setting and getting login sessions
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
                //
                //    Return whether or not a player has been logged in for that session
                //    Should never return null
                //
                case "IsLoggedIn":

                    UUID user = UUID.fromString(in.readUTF());
                    for(String info : ProxyServer.getInstance().getServers().keySet()){
                        ServerInfo i = ProxyServer.getInstance().getServerInfo(info);
                        ByteArrayDataOutput out = ByteStreams.newDataOutput();

                        out.writeUTF("IsLoggedIn");
                        out.writeUTF(user.toString());

                        out.writeBoolean(LoginBungeeCord.getLoginManager().isLoggedIn(user));

                        i.sendData("Login", out.toByteArray());
                    }



                    break;
                //
                //    Set the logged in status for a player's network session
                //
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
