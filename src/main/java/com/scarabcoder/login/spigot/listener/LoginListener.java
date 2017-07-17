package com.scarabcoder.login.spigot.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.scarabcoder.login.CachedPlayerData;
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
 * Cache player data from the database and get the login session from the proxy plugin
 */
public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){


        LoginSpigot.getLoginManager().setLoggedIn(e.getPlayer().getUniqueId(), false);
        final Player p = e.getPlayer();
        CachedPlayerData d = DataManager.getPlayerData(p.getUniqueId());
        DataManager.refreshCache(e.getPlayer().getUniqueId());
        if(d.isPremiumAccount() && d.getHashedPassword() == null){
            p.sendMessage(ChatColor.RED + "Your account isn't secure - set a password with /register <password>.");
            LoginSpigot.getLoginManager().setLoggedIn(p.getUniqueId(), true);

        }
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("IsLoggedIn");
                out.writeUTF(p.getUniqueId().toString());

                p.sendPluginMessage(LoginSpigot.getPlugin(), "Login", out.toByteArray());



            }
        };
        r.runTaskLater(LoginSpigot.getPlugin(), 5);



    }

}
