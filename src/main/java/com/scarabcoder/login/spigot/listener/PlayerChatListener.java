package com.scarabcoder.login.spigot.listener;

import com.scarabcoder.login.spigot.LoginSpigot;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Anastasia on 7/8/17.
 */
public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        if(!LoginSpigot.getLoginManager().isLoggedIn(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
            LoginSpigot.sendLoginNotification(e.getPlayer());
        }
    }

}
