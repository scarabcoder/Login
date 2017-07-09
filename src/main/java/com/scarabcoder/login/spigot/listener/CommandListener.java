package com.scarabcoder.login.spigot.listener;

import com.scarabcoder.login.spigot.LoginSpigot;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Anastasia on 7/8/17.
 */
public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){

        if(!LoginSpigot.getLoginManager().isLoggedIn(e.getPlayer().getUniqueId())){
            if(e.getMessage().startsWith("/register") || e.getMessage().startsWith("/login"))
                return;
            e.setCancelled(true);
            LoginSpigot.sendLoginNotification(e.getPlayer());

        }
    }

}
