package com.scarabcoder.login.bungeecord.listener;

import com.scarabcoder.login.bungeecord.LoginBungeeCord;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Remove sessions
 */
public class PlayerLogoutListener implements Listener {

    //
    //    Remove the session from memory, to prevent memory leaks.
    //
    @EventHandler
    public void onLogout(PlayerDisconnectEvent e){
        LoginBungeeCord.getLoginManager().removeEntry(e.getPlayer().getUniqueId());
    }

}
