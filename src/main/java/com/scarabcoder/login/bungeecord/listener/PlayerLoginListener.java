package com.scarabcoder.login.bungeecord.listener;

import com.scarabcoder.login.MySQLManager;
import com.scarabcoder.login.UUIDUtil;
import com.scarabcoder.login.bungeecord.LoginBungeeCord;
import com.scarabcoder.login.bungeecord.LoginManager;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Anastasia on 7/8/17.
 */
public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PreLoginEvent e){

        Connection c = MySQLManager.getConnection();

        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username=?");

            ps.setString(1, e.getConnection().getName());

            ResultSet st = ps.executeQuery();

            if(st.next()){
                if(!e.getConnection().isOnlineMode())
                    e.getConnection().setUniqueId(UUID.fromString(st.getString("uuid")));
            }else{

                boolean isPremium = e.getConnection().isOnlineMode();

                UUID uuid = isPremium ? e.getConnection().getUniqueId() : UUID.randomUUID();

                ps = c.prepareStatement("INSERT INTO users (uuid, username, premium) VALUES (?, ?, ?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, e.getConnection().getName());
                ps.setBoolean(3, isPremium);

                ps.executeUpdate();

                if(!isPremium)
                    e.getConnection().setUniqueId(uuid);
            }

            LoginBungeeCord.getLoginManager().registerNewUser(e.getConnection().getUniqueId());

        } catch (SQLException e1) {
            e1.printStackTrace();
        }


    }
}
