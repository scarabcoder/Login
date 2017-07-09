package com.scarabcoder.login.spigot.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.scarabcoder.login.spigot.LoginSpigot;
import com.scarabcoder.login.spigot.manager.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Anastasia on 7/8/17.
 */
public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){


        DataManager.refreshCache(e.getPlayer().getUniqueId());
        final Player p = e.getPlayer();
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("IsLoggedIn");
                out.writeUTF(p.getUniqueId().toString());

                p.sendPluginMessage(LoginSpigot.getPlugin(), "Login", out.toByteArray());
                p.sendMessage(ChatColor.GREEN + "Checking login status...");
            }
        };
        r.runTaskLater(LoginSpigot.getPlugin(), 5);


        LoginSpigot.getLoginManager().setLoggedIn(e.getPlayer().getUniqueId(), false);

    }

}
