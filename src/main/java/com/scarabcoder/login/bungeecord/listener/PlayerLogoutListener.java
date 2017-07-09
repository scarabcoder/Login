package com.scarabcoder.login.bungeecord.listener;

import com.scarabcoder.login.bungeecord.LoginBungeeCord;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Anastasia on 7/8/17.
 */
public class PlayerLogoutListener implements Listener {

    @EventHandler
    public void onLogout(PlayerDisconnectEvent e){
        LoginBungeeCord.getLoginManager().setLoggedIn(e.getPlayer().getUniqueId(), false);
    }

}
