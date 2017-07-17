package com.scarabcoder.login.spigot.listener;

import com.scarabcoder.login.spigot.LoginSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Listeners that block non-logged in players from doing certain actions.
 */
public class BlockListeners implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){

        if(!LoginSpigot.getLoginManager().isLoggedIn(e.getPlayer().getUniqueId())){
            if(e.getMessage().startsWith("/register") || e.getMessage().startsWith("/login"))
                return;
            e.setCancelled(true);
            LoginSpigot.sendLoginNotification(e.getPlayer());

        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        if(!LoginSpigot.getLoginManager().isLoggedIn(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
            LoginSpigot.sendLoginNotification(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(!LoginSpigot.getLoginManager().isLoggedIn(e.getPlayer().getUniqueId())){
            e.getPlayer().teleport(e.getFrom());
            LoginSpigot.sendLoginNotification(e.getPlayer());
        }
    }

}
